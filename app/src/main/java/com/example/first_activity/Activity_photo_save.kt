package com.example.first_activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Activity_photo_save : AppCompatActivity() {

    var foto_guardar: ImageView? = null
    //var btn_guardar: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_photo_save)

        foto_guardar = findViewById<View?>(R.id.foto) as ImageView
        //btn_guardar = findViewById<View?>(R.id.foto_guardar) as Button


        val btn_guardar: Button = findViewById(R.id.foto_guardar)
        btn_guardar.setOnClickListener {
            photo()
        }

    }

    private fun photo() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(
                    this, arrayOf<String>(Manifest.permission.CAMERA),
                    MainActivity.REQUEST_CAMERA_PERMISSION
                )
            }else{
                    openCamera();
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if (requestCode == MainActivity.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MainActivity.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data!!.getExtras()
            val img = extras!!.get("data") as Bitmap?

            val foto_guar = findViewById<View?>(R.id.foto) as ImageView
            foto_guar.setImageBitmap(img)
        }
    }

    private fun saveImg(){

    }


}