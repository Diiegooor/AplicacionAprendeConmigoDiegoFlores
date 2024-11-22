package com.example.aprendeconmigo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class editar extends AppCompatActivity {

    private EditText ed_nombre, ed_apellido, ed_edad, ed_id;
    private Button b_editar, b_eliminar, b_volver;

    private FirebaseFirestore db; // Referencia a Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_edad = findViewById(R.id.et_edad);
        ed_id = findViewById(R.id.id);

        b_editar = findViewById(R.id.btn_editar);
        b_eliminar = findViewById(R.id.btn_eliminar);
        b_volver = findViewById(R.id.btn_volver);

        // Obtener datos enviados desde otra actividad
        Intent i = getIntent();
        String et_id = i.getStringExtra("id");
        String et_nombre = i.getStringExtra("nombre");
        String et_apellido = i.getStringExtra("apellido");
        String et_edad = i.getStringExtra("edad");

        // Establecer datos en los campos de texto
        ed_id.setText(et_id);
        ed_nombre.setText(et_nombre);
        ed_apellido.setText(et_apellido);
        ed_edad.setText(et_edad);

        // Botón para editar
        b_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
            }
        });

        // Botón para eliminar
        b_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });

        // Botón para volver
        b_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ver_usuarios.class);
                startActivity(i);
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
                    limpiarCampos();

                    // Redirigir a la actividad donde se muestran los usuarios después de eliminar
                    Intent intent = new Intent(editar.this, ver_usuarios.class);
                    startActivity(intent);
                    finish(); // Cierra la actividad actual para que no regrese al eliminar el usuario
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

        if (id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG).show();
            return;
        }

        // Crear un mapa con los nuevos datos
        db.collection("usuarios").document(id)
                .update("nombre", nombre, "apellido", apellido, "edad", edad)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Usuario actualizado correctamente.", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al actualizar los datos.", Toast.LENGTH_LONG).show();
                });
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        ed_id.setText("");
        ed_nombre.setText("");
        ed_apellido.setText("");
        ed_edad.setText("");
        ed_nombre.requestFocus();
    }
}
