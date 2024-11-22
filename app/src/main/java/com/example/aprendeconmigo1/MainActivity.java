package com.example.aprendeconmigo1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText ed_nombre, ed_contrasena;
    private Button btn_iniciar;

    private FirebaseFirestore db; // Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        ed_nombre = findViewById(R.id.et_nombre);
        ed_contrasena = findViewById(R.id.et_contrasena);
        btn_iniciar = findViewById(R.id.btn_iniciar);
        btnRegister = findViewById(R.id.btn_register);

        // Botón para iniciar sesión
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        // Botón para ir al registro
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

        if (nombre.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Consultar Firestore
        db.collection("usuarios")
                .whereEqualTo("nombre", nombre)
                .whereEqualTo("contrasena", contrasena)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        // Ir al menú principal
                        Intent intent = new Intent(this, menuPrincipal1.class);
                        startActivity(intent);
                        finish(); // Opcional: cerrar la actividad actual
                    } else {
                        Toast.makeText(this, "Nombre o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al conectar con Firebase", Toast.LENGTH_SHORT).show();
                });
    }
}
