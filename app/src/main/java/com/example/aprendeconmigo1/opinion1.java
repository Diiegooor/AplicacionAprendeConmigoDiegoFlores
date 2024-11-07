package com.example.aprendeconmigo1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class opinion1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion1);

        Spinner opinionSpinner = findViewById(R.id.opinionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opinion_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opinionSpinner.setAdapter(adapter);

        EditText opinionText = findViewById(R.id.opinionText);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button submitButton = findViewById(R.id.submitOpinionButton);

        // Crear base de datos y tabla si no existen
        createDatabaseAndTables();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opinion = opinionText.getText().toString();
                float rating = ratingBar.getRating();
                String category = opinionSpinner.getSelectedItem().toString();

                // Guardar en la base de datos
                saveOpinion(category, opinion, rating);
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

    private void createDatabaseAndTables() {
        try {
            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE, null);

            // Crear tabla persona si no existe
            db.execSQL("CREATE TABLE IF NOT EXISTS persona(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre VARCHAR, apellido VARCHAR, edad VARCHAR, contrasena VARCHAR)");

            // Crear tabla de opiniones si no existe
            db.execSQL("CREATE TABLE IF NOT EXISTS opinion(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "categoria VARCHAR, opinion TEXT, calificacion REAL)");

            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al crear la base de datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void saveOpinion(String category, String opinion, float rating) {
        try {
            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE, null);

            String sql = "INSERT INTO opinion(categoria, opinion, calificacion) VALUES (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, category);
            statement.bindString(2, opinion);
            statement.bindDouble(3, rating);
            statement.execute();
            db.close();

            Toast.makeText(this, "Opinión guardada satisfactoriamente.", Toast.LENGTH_LONG).show();

            // Limpiar campos después de guardar
            ((EditText) findViewById(R.id.opinionText)).setText("");
            ((RatingBar) findViewById(R.id.ratingBar)).setRating(0);
            ((Spinner) findViewById(R.id.opinionSpinner)).setSelection(0);
        } catch (Exception ex) {
            Toast.makeText(this, "Error al guardar la opinión: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
