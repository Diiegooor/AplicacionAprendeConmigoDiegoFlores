package com.example.aprendeconmigo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class opinion1 extends AppCompatActivity {

    private FirebaseFirestore db; // Referencia a Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion1);

        db = FirebaseFirestore.getInstance(); // Inicializar Firestore

        Spinner opinionSpinner = findViewById(R.id.opinionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opinion_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opinionSpinner.setAdapter(adapter);

        EditText opinionText = findViewById(R.id.opinionText);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button submitButton = findViewById(R.id.submitOpinionButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opinion = opinionText.getText().toString();
                float rating = ratingBar.getRating();
                String category = opinionSpinner.getSelectedItem().toString();

                // Validar campos antes de guardar
                if (opinion.isEmpty() || category.isEmpty() || rating == 0) {
                    Toast.makeText(opinion1.this, "Por favor completa todos los campos.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Guardar la opinión en Firestore
                saveOpinionToFirestore(category, opinion, rating);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button verOpinionesButton = findViewById(R.id.verOpinionesButton);
        verOpinionesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(opinion1.this, VerOpinionesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveOpinionToFirestore(String category, String opinion, float rating) {
        // Crear un mapa con los datos de la opinión
        Map<String, Object> opinionData = new HashMap<>();
        opinionData.put("categoria", category);
        opinionData.put("opinion", opinion);
        opinionData.put("calificacion", rating);

        // Agregar la opinión a la colección "opiniones" en Firestore
        db.collection("opiniones")
                .add(opinionData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Opinión guardada satisfactoriamente.", Toast.LENGTH_LONG).show();

                    // Limpiar campos después de guardar
                    ((EditText) findViewById(R.id.opinionText)).setText("");
                    ((RatingBar) findViewById(R.id.ratingBar)).setRating(0);
                    ((Spinner) findViewById(R.id.opinionSpinner)).setSelection(0);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al guardar la opinión: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
