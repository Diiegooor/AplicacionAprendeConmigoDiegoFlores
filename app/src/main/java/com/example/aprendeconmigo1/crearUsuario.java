package com.example.aprendeconmigo1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class crearUsuario extends AppCompatActivity {

    private EditText ed_nombre, ed_apellido, ed_edad, ed_contrasena; // Campos del formulario
    private Button b_agregar, b_ver;

    private FirebaseFirestore db; // Referencia a Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_edad = findViewById(R.id.et_edad);
        ed_contrasena = findViewById(R.id.et_Contrasena);

        b_agregar = findViewById(R.id.btn_agregar);
        b_ver = findViewById(R.id.btn_ver);

        // Botón para ver usuarios
        b_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ver_usuarios.class);
                startActivity(i);
            }
        });

        // Botón para agregar usuario
        b_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CrearUsuario", "Botón de agregar presionado");
                insertar();
            }
        });

        // Botón para regresar
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Método para insertar un usuario en Firestore
    public void insertar() {
        Log.d("CrearUsuario", "Método insertar ejecutado");

        // Obtener datos de los campos
        String nombre = ed_nombre.getText().toString().trim();
        String apellido = ed_apellido.getText().toString().trim();
        String edad = ed_edad.getText().toString().trim();
        String contrasena = ed_contrasena.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG).show();
            return;
        }

        // Log para verificar los datos antes de la consulta
        Log.d("CrearUsuario", "Datos ingresados: nombre = " + nombre + ", apellido = " + apellido + ", edad = " + edad);

        // Buscar si ya existe un usuario con el mismo nombre (o correo si lo prefieres)
        db.collection("usuarios")
                .whereEqualTo("nombre", nombre) // O usa un campo único como correo
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Si el usuario ya existe, mostrar mensaje
                        Toast.makeText(this, "Este usuario ya existe.", Toast.LENGTH_LONG).show();
                        Log.d("CrearUsuario", "El usuario ya existe.");
                    } else {
                        // Si el usuario no existe, agregarlo
                        Log.d("CrearUsuario", "El usuario no existe, se agregará.");

                        Map<String, Object> usuario = new HashMap<>();
                        usuario.put("nombre", nombre);
                        usuario.put("apellido", apellido);
                        usuario.put("edad", edad);
                        usuario.put("contrasena", contrasena);

                        // Guardar en Firestore
                        db.collection("usuarios")
                                .add(usuario) // Guardar el mapa en la colección "usuarios"
                                .addOnSuccessListener(documentReference -> {
                                    Log.d("CrearUsuario", "Usuario agregado con éxito");
                                    // Mostrar mensaje de éxito antes de redirigir
                                    Toast.makeText(this, "Usuario creado correctamente.", Toast.LENGTH_LONG).show();

                                    // Limpiar los campos del formulario
                                    ed_nombre.setText("");
                                    ed_apellido.setText("");
                                    ed_edad.setText("");
                                    ed_contrasena.setText("");

                                    // Redirigir al usuario a la página de inicio de sesión
                                    Intent intent = new Intent(crearUsuario.this, MainActivity.class); // Cambia LoginActivity por la actividad de inicio de sesión
                                    startActivity(intent);
                                    finish(); // Cierra la actividad actual para que el usuario no regrese con el botón "Atrás"
                                })
                                .addOnFailureListener(e -> {
                                    Log.d("CrearUsuario", "Error al guardar en Firestore");
                                    Toast.makeText(this, "Error al guardar los datos en Firestore.", Toast.LENGTH_LONG).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("CrearUsuario", "Error al verificar el usuario");
                    Toast.makeText(this, "Error al verificar el usuario.", Toast.LENGTH_LONG).show();
                });
    }
}
