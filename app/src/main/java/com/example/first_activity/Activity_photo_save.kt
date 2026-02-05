package com.example.first_activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Activity_photo_save : AppCompatActivity() {

    var foto_guardar: ImageView? = null
    var btn_guardar: Button? = null

    object RequestCodes {
        const val CAMERA_PERMISSION = 230
        const val IMAGE_CAPTURE = 191
    }
   // startActivityForResult(intent, RequestCodes.IMAGE_CAPTURE)  ASI LO USARIA

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
        Toast.makeText(getApplicationContext(), "Estas ejecutando el onclick", Toast.LENGTH_LONG).show()
    }
}