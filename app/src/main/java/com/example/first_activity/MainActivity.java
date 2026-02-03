package com.example.first_activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.first_activity.configuration.SQLiteConexion;
import com.example.first_activity.configuration.transacciones;

public class MainActivity extends AppCompatActivity {

    EditText nombres, apellidos, edad, correo;

    ImageView foto;

    Button btn_agregar;

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

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_LONG).show();
                addPerson();
                clearEditText();
            }
        });
    }

    private void addPerson() {
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
    }

    private void clearEditText() {

        nombres.setText("");
        apellidos.setText("");
        edad.setText("");
        correo.setText("");

        nombres.requestFocus();
    }
}