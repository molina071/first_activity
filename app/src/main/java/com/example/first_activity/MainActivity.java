package com.example.first_activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import com.example.first_activity.configuration.SQLiteConexion;
import com.example.first_activity.configuration.transacciones;

public class MainActivity extends AppCompatActivity {

    EditText nombres, apellidos, edad, correo;
    ImageView foto;
    Button btn_agregar, btn_foto;

        static final int REQUEST_CAMERA_PERMISSION = 230;
        static final int REQUEST_IMAGE_CAPTURE = 191;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nombres = (EditText) findViewById(R.id.nombre);
        apellidos = (EditText) findViewById(R.id.apellido);
        edad = (EditText) findViewById(R.id.edad);
        correo = (EditText) findViewById(R.id.correo);
        foto = (ImageView) findViewById(R.id.Foto);
        btn_agregar = (Button) findViewById(R.id.btn_agregar);
        btn_foto = (Button) findViewById(R.id.btnFoto);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerson();
                clearEditText();
            }
        });

        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photo();

            }
        });
    }

    private void photo() {
       if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
               != PackageManager.PERMISSION_GRANTED)
       {
           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                   REQUEST_CAMERA_PERMISSION);
       }else{
           openCamera();
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        if(requestCode == REQUEST_CAMERA_PERMISSION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap img = (Bitmap) extras.get("data");
            foto.setImageBitmap(img);
        }
    }

    private void addPerson() {

        try{
            SQLiteConexion conexion = new SQLiteConexion(this, transacciones.dbName, null, transacciones.version);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(transacciones.nombre, nombres.getText().toString());
            valores.put(transacciones.apellido, apellidos.getText().toString());
            valores.put(transacciones.edad, edad.getText().toString());
            valores.put(transacciones.correo, correo.getText().toString());
            valores.put(transacciones.foto, "");

            Long resultado = db.insert(transacciones.tbPersons, transacciones.id, valores);

            Toast.makeText(getApplicationContext(), "Registro Ingresado" + resultado.toString(),
                    Toast.LENGTH_LONG).show();
            db.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Ah ocurrido un error" + e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void clearEditText() {

        nombres.setText("");
        apellidos.setText("");
        edad.setText("");
        correo.setText("");

        nombres.requestFocus();
    }



}