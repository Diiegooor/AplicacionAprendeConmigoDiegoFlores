package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class VerOpinionesActivity extends AppCompatActivity {

    private ListView listViewOpiniones;
    private ArrayList<String> opinionesList;
    private ArrayAdapter<String> adapter;
    private FirebaseFirestore db; // Referencia a Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_opiniones);

        listViewOpiniones = findViewById(R.id.listViewOpiniones);
        opinionesList = new ArrayList<>();
        db = FirebaseFirestore.getInstance(); // Inicializar Firestore

        // Configurar el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opinionesList);
        listViewOpiniones.setAdapter(adapter);

        // Llamar al método para cargar las opiniones desde Firestore
        cargarOpiniones();

        // Configurar botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void cargarOpiniones() {
        // Consultar la colección "opiniones" en Firestore
        db.collection("opiniones")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Limpiar la lista antes de agregar nuevos datos
                    opinionesList.clear();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String categoria = document.getString("categoria");
                        String opinion = document.getString("opinion");
                        Double calificacion = document.getDouble("calificacion");

                        // Formatear cada opinión para mostrar en el ListView
                        String opinionTexto = "Categoría: " + categoria + "\nOpinión: " + opinion + "\nCalificación: " + calificacion;
                        opinionesList.add(opinionTexto);
                    }

                    // Notificar al adaptador que los datos han cambiado
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar opiniones: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
