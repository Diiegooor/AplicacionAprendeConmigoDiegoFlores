package com.example.aprendeconmigo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister; // Declarar el botón
    private EditText ed_nombre, ed_contrasena;
    private Button btn_iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_nombre = findViewById(R.id.et_nombre);
        ed_contrasena = findViewById(R.id.et_contrasena);
        btn_iniciar = findViewById(R.id.btn_iniciar);

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        btnRegister = findViewById(R.id.btn_register); // Inicializar el botón

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, crearUsuario.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarSesion() {
        String nombre = ed_nombre.getText().toString().trim();
        String contrasena = ed_contrasena.getText().toString().trim();

        // Verificar que los campos no estén vacíos
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
            return; // Salir del método si hay campos vacíos
        }

        SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM persona WHERE nombre = ? AND contrasena = ?", new String[]{nombre, contrasena});

        if (cursor.getCount() > 0) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

            // Crear un Intent para iniciar la nueva actividad
            Intent intent = new Intent(this, menuPrincipal1.class);
            startActivity(intent); // Iniciar la nueva actividad

            // Si quieres cerrar la actividad actual, puedes llamar a finish()
            // finish();
        } else {
            Toast.makeText(this, "Nombre o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}

