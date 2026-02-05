package com.example.first_activity

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

class JC_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaCámara()
                }
            }
        }
    }
}

@Composable
fun PantallaCámara() {
    val context = LocalContext.current
    
    // vista previa y el modo
    var fotoPreview by remember { mutableStateOf<Bitmap?>(null) }
    var videoUriActual by remember { mutableStateOf<Uri?>(null) }
    var esModoFoto by remember { mutableStateOf(true) } // Switch

    // capturar Foto
    val launcherFoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotoPreview = bitmap
            videoUriActual = null
            guardarFoto(context, bitmap) // Lógica de guardado automático
        }
    }

    // capturar Video
    val launcherVideo = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { exito ->
        if (exito) {
            Toast.makeText(context, "Video guardado en el sistema", Toast.LENGTH_SHORT).show()
            fotoPreview = null
        }
    }

    // solicitar permisos
    val launcherPermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        if (permisos[Manifest.permission.CAMERA] == true) {
            Toast.makeText(context, "Permiso concedido, intente de nuevo", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Cámara con Jetpack Compose", style = MaterialTheme.typography.headlineSmall)
        
        Spacer(modifier = Modifier.height(20.dp))

        // Selector de modo: Foto o Video
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Video")
            Switch(checked = esModoFoto, onCheckedChange = { esModoFoto = it })
            Text("Foto")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón Único
        Button(
            onClick = {
                // Validación de permisos
                val tieneCamara = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                val tieneAudio = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

                if (tieneCamara && (esModoFoto || tieneAudio)) {
                    if (esModoFoto) {
                        launcherFoto.launch()
                    } else {
                        val uri = crearUriVideo(context)
                        videoUriActual = uri
                        uri?.let { launcherVideo.launch(it) }
                    }
                } else {
                    launcherPermisos.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (esModoFoto) "Guardar" else "Guardar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Vista previa de lo capturado
        if (esModoFoto && fotoPreview != null) {
            Image(bitmap = fotoPreview!!.asImageBitmap(), contentDescription = null, modifier = Modifier.size(250.dp))
        } else if (!esModoFoto && videoUriActual != null) {
            Text("Video guardado en: ${videoUriActual?.path}")
        }
    }
}

// guardar Foto
fun guardarFoto(context: Context, bitmap: Bitmap) {
    val valores = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "Foto_JC_${System.currentTimeMillis()}.png")
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    }
    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valores)
    try {
        uri?.let {
            context.contentResolver.openOutputStream(it)?.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            }
            Toast.makeText(context, "Foto guardada en Galería", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show()
    }
}

//destino del Video
fun crearUriVideo(context: Context): Uri? {
    val valores = ContentValues().apply {
        put(MediaStore.Video.Media.DISPLAY_NAME, "Video_JC_${System.currentTimeMillis()}.mp4")
        put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
    }
    return context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, valores)
}
