package com.example.first_activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Activity_video_save : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video_save)


       val grabar = findViewById<View?>(R.id.imgGrabar) as ImageView
        grabar.setOnClickListener {
            video();
        }

    }

    private fun video(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this, arrayOf<String>(Manifest.permission.CAMERA),
                MainActivity.REQUEST_CAMERA_PERMISSION
            )
        }else{
            recordVideo();
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recordVideo()
        } else {
            Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show()
        }
    }
    private fun recordVideo() {
        val REQUEST_VIDEO_CAPTURE = 1
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { video ->
            video.resolveActivity(packageManager)?.also {
                startActivityForResult(video, REQUEST_VIDEO_CAPTURE)
            }
        }

    }
}