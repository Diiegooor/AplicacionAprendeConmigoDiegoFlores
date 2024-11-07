package com.example.aprendeconmigo1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class crearUsuario extends AppCompatActivity {

    private EditText ed_nombre, ed_apellido, ed_edad, ed_contrasena; // Añadir campo de contraseña
    private Button b_agregar, b_ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_edad = findViewById(R.id.et_edad);
        ed_contrasena = findViewById(R.id.et_Contrasena); // Inicializar campo de contraseña

        b_agregar = findViewById(R.id.btn_agregar);
        b_ver = findViewById(R.id.btn_ver);

        // botón ver
        b_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ver_usuarios.class);
                startActivity(i);
            }
        });

        // botón agregar registro
        b_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // método insertar registro
    public void insertar() {
        try {
            String nombre = ed_nombre.getText().toString();
            String apellido = ed_apellido.getText().toString();
            String edad = ed_edad.getText().toString();
            String contrasena = ed_contrasena.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE, null);

            // Crear tabla persona si no existe
            db.execSQL("CREATE TABLE IF NOT EXISTS persona(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre VARCHAR, apellido VARCHAR, edad VARCHAR, contrasena VARCHAR)");

            // Crear tabla de opiniones si no existe
            db.execSQL("CREATE TABLE IF NOT EXISTS opinion(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "categoria VARCHAR, opinion TEXT, calificacion REAL)");

            // Insertar en la tabla persona
            String sql = "INSERT INTO persona(nombre, apellido, edad, contrasena) VALUES (?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, nombre);
            statement.bindString(2, apellido);
            statement.bindString(3, edad);
            statement.bindString(4, contrasena);
            statement.execute();

            Toast.makeText(this, "Datos agregados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();

            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_edad.setText("");
            ed_contrasena.setText("");
            ed_nombre.requestFocus();
        } catch (Exception ex) {
            Toast.makeText(this, "Error: no se pudieron guardar los datos.", Toast.LENGTH_LONG).show();
        }
    }

}
