package com.example.aprendeconmigo1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class editar extends AppCompatActivity {

    private EditText ed_nombre, ed_apellido, ed_edad, ed_contrasena, ed_id;
    private Button b_editar, b_eliminar;

    private FirebaseFirestore db; // Referencia a Firestore
    private static final String TAG = "EditarUsuario"; // Para logs de depuración

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Inicializar vistas
        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_edad = findViewById(R.id.et_edad);
        ed_contrasena = findViewById(R.id.et_contrasena);
        ed_id = findViewById(R.id.id);

        b_editar = findViewById(R.id.btn_editar);
        b_eliminar = findViewById(R.id.btn_eliminar);

        // Obtener datos enviados desde otra actividad
        Intent i = getIntent();
        String et_id = i.getStringExtra("id");
        String et_nombre = i.getStringExtra("nombre");
        String et_apellido = i.getStringExtra("apellido");
        String et_edad = i.getStringExtra("edad");
        String et_contrasena = i.getStringExtra("contrasena");

        // Validar y asignar datos a los campos
        if (et_id != null) ed_id.setText(et_id);
        if (et_nombre != null) ed_nombre.setText(et_nombre);
        if (et_apellido != null) ed_apellido.setText(et_apellido);
        if (et_edad != null) ed_edad.setText(et_edad);
        if (et_contrasena != null) ed_contrasena.setText(et_contrasena);

        // Botón para editar
        b_editar.setOnClickListener(view -> editar());

        // Botón para eliminar
        b_eliminar.setOnClickListener(view -> eliminar());

        // Botón para regresar
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    // Método para eliminar un usuario
    public void eliminar() {
        String id = ed_id.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "ID no válido", Toast.LENGTH_LONG).show();
            return;
        }

        db.collection("usuarios").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Usuario eliminado correctamente.", Toast.LENGTH_LONG).show();
                    finish(); // Cierra la actividad actual
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al eliminar el usuario.", Toast.LENGTH_LONG).show();
                });
    }

    // Método para editar un usuario
    public void editar() {
        String id = ed_id.getText().toString().trim();
        String nombre = ed_nombre.getText().toString().trim();
        String apellido = ed_apellido.getText().toString().trim();
        String edad = ed_edad.getText().toString().trim();
        String contrasena = ed_contrasena.getText().toString().trim();

        if (id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG).show();
            return;
        }

        // Crear un mapa con los nuevos datos y actualizar en Firestore
        db.collection("usuarios").document(id)
                .update("nombre", nombre, "apellido", apellido, "edad", edad, "contrasena", contrasena)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Usuario actualizado correctamente.", Toast.LENGTH_LONG).show();
                    finish(); // Cierra la actividad actual
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al actualizar los datos.", Toast.LENGTH_LONG).show();
                });
    }
}
