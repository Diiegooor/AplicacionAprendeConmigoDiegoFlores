package com.example.aprendeconmigo1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;
import java.util.Map;

public class crearUsuario extends AppCompatActivity {

    private EditText ed_nombre, ed_apellido, ed_edad, ed_contrasena; // Campos del formulario
    private Button b_agregar, b_ver;
    private ProgressBar progressBar; // Indicador de carga
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
        ed_contrasena = findViewById(R.id.et_contrasena);
        b_agregar = findViewById(R.id.btn_agregar);
        b_ver = findViewById(R.id.btn_ver);
        progressBar = findViewById(R.id.progressBar);

        // Botón para ver usuarios
        b_ver.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ver_usuarios.class);
            startActivity(i);
        });

        // Botón para agregar usuario
        b_agregar.setOnClickListener(v -> {
            if (validarCampos()) {
                insertar(); // Si la validación es correcta, insertar el usuario
            }
        });

        // Botón para regresar
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    // Método para validar los campos
    private boolean validarCampos() {
        String nombre = ed_nombre.getText().toString().trim();
        String apellido = ed_apellido.getText().toString().trim();
        String edad = ed_edad.getText().toString().trim();
        String contrasena = ed_contrasena.getText().toString().trim();

        if (nombre.isEmpty()) {
            ed_nombre.setError("El nombre no puede estar vacío");
            return false;
        }
        if (apellido.isEmpty()) {
            ed_apellido.setError("El apellido no puede estar vacío");
            return false;
        }
        if (edad.isEmpty() || !edad.matches("\\d+") || Integer.parseInt(edad) <= 0) {
            ed_edad.setError("Introduce una edad válida");
            return false;
        }
        if (!contrasena.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            ed_contrasena.setError("La contraseña debe tener al menos 8 caracteres, una mayúscula y un número");
            return false;
        }

        return true;
    }

    // Método para insertar un usuario en Firestore
    public void insertar() {
        Log.d("CrearUsuario", "Método insertar ejecutado");

        // Mostrar el indicador de carga
        progressBar.setVisibility(View.VISIBLE);

        // Obtener datos de los campos
        String nombre = ed_nombre.getText().toString().trim();
        String apellido = ed_apellido.getText().toString().trim();
        String edad = ed_edad.getText().toString().trim();
        String contrasena = ed_contrasena.getText().toString().trim();

        // Buscar si ya existe un usuario con el mismo nombre
        db.collection("usuarios")
                .whereEqualTo("nombre", nombre) // O usa un campo único como correo
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE); // Ocultar el indicador de carga

                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Si el usuario ya existe, mostrar mensaje
                        Snackbar.make(findViewById(android.R.id.content), "Este usuario ya existe.", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(getResources().getColor(R.color.purple_500))
                                .setTextColor(getResources().getColor(android.R.color.white))
                                .show();
                    } else {
                        // Si el usuario no existe, agregarlo
                        Map<String, Object> usuario = new HashMap<>();
                        usuario.put("nombre", nombre);
                        usuario.put("apellido", apellido);
                        usuario.put("edad", edad);
                        usuario.put("contrasena", contrasena);

                        db.collection("usuarios")
                                .add(usuario)
                                .addOnSuccessListener(documentReference -> {
                                    // Usuario agregado exitosamente
                                    Snackbar.make(findViewById(android.R.id.content), "Usuario creado correctamente.", Snackbar.LENGTH_LONG)
                                            .setBackgroundTint(getResources().getColor(R.color.purple_500))
                                            .setTextColor(getResources().getColor(android.R.color.white))
                                            .show();

                                    // Limpiar los campos
                                    ed_nombre.setText("");
                                    ed_apellido.setText("");
                                    ed_edad.setText("");
                                    ed_contrasena.setText("");

                                    // Redirigir al inicio de sesión
                                    new Handler().postDelayed(() -> {
                                        Intent intent = new Intent(crearUsuario.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }, 1000); // Espera de 1 segundo antes de redirigir
                                })
                                .addOnFailureListener(e -> {
                                    progressBar.setVisibility(View.GONE);
                                    Snackbar.make(findViewById(android.R.id.content), "Error al guardar en Firestore.", Snackbar.LENGTH_LONG)
                                            .setBackgroundTint(getResources().getColor(R.color.purple_500))
                                            .setTextColor(getResources().getColor(android.R.color.white))
                                            .show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(findViewById(android.R.id.content), "Error al verificar el usuario.", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.purple_500))
                            .setTextColor(getResources().getColor(android.R.color.white))
                            .show();
                });
    }
}
