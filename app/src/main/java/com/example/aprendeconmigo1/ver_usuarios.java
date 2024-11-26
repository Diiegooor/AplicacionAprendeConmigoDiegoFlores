package com.example.aprendeconmigo1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ver_usuarios extends AppCompatActivity {

    private ListView lst1;
    private ArrayList<Persona> personas = new ArrayList<>();
    private PersonaAdapter personaAdapter;
    private FirebaseFirestore db; // Referencia a Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuarios);

        lst1 = findViewById(R.id.lst1);
        db = FirebaseFirestore.getInstance(); // Inicializar Firestore

        // Configurar el adaptador personalizado
        personaAdapter = new PersonaAdapter(this, personas);
        lst1.setAdapter(personaAdapter);

        // Cargar los usuarios desde Firestore
        cargarUsuarios();

        // Configurar el evento de clic en los elementos de la lista
        lst1.setOnItemClickListener((adapterView, view, position, l) -> {
            if (position >= 0 && position < personas.size()) {
                Persona personaSeleccionada = personas.get(position);

                if (personaSeleccionada != null) {
                    // Redirigir a la actividad de edición con los datos del usuario
                    Intent i = new Intent(getApplicationContext(), editar.class);
                    i.putExtra("id", personaSeleccionada.getId());
                    i.putExtra("nombre", personaSeleccionada.getNombre());
                    i.putExtra("apellido", personaSeleccionada.getApellido());
                    i.putExtra("edad", personaSeleccionada.getEdad());
                    i.putExtra("contrasena", personaSeleccionada.getContrasena());
                    startActivity(i);
                } else {
                    Toast.makeText(ver_usuarios.this, "Error al seleccionar el usuario.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ver_usuarios.this, "Índice inválido seleccionado.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void cargarUsuarios() {
        // Consultar la colección "usuarios" en Firestore
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    personas.clear(); // Limpiar la lista antes de agregar nuevos datos

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String id = document.getId(); // ID único del documento
                        String nombre = document.getString("nombre");
                        String apellido = document.getString("apellido");
                        String edad = document.getString("edad");
                        String contrasena = document.getString("contrasena");

                        if (id != null && nombre != null && apellido != null && edad != null && contrasena != null) {
                            personas.add(new Persona(id, nombre, apellido, edad, contrasena));
                        }
                    }

                    // Notificar al adaptador que los datos han cambiado
                    personaAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar usuarios: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}


