package com.example.aprendeconmigo1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ver_usuarios extends AppCompatActivity {

    private ListView lst1;
    private ArrayList<String> arreglo = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private FirebaseFirestore db; // Referencia a Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuarios);

        lst1 = findViewById(R.id.lst1);
        db = FirebaseFirestore.getInstance(); // Inicializar Firestore

        // Configurar el adaptador de la lista
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arreglo);
        lst1.setAdapter(arrayAdapter);

        // Cargar los usuarios desde Firestore
        cargarUsuarios();

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String usuarioInfo = arreglo.get(position);
                String[] usuarioDatos = usuarioInfo.split(" \t ");

                // Extraer datos del usuario (id, nombre, apellido, edad)
                String id = usuarioDatos[0];
                String nombre = usuarioDatos[1];
                String apellido = usuarioDatos[2];
                String edad = usuarioDatos[3];

                // Enviar los datos a la actividad editar
                Intent i = new Intent(getApplicationContext(), editar.class);
                i.putExtra("id", id);
                i.putExtra("nombre", nombre);
                i.putExtra("apellido", apellido);
                i.putExtra("edad", edad);
                startActivity(i);
            }
        });

        // Configurar el botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void cargarUsuarios() {
        // Consultar la colección "usuarios" en Firestore
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Limpiar la lista antes de agregar nuevos datos
                    arreglo.clear();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String nombre = document.getString("nombre");
                        String apellido = document.getString("apellido");
                        String edad = document.getString("edad");

                        // Formatear la información del usuario para mostrar solo nombre, apellido y edad
                        String usuarioInfo = nombre + " " + apellido + "    Edad: " + edad;
                        arreglo.add(usuarioInfo);
                    }

                    // Notificar al adaptador que los datos han cambiado
                    arrayAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar usuarios: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
