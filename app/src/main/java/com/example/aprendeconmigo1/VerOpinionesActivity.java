package com.example.aprendeconmigo1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class VerOpinionesActivity extends AppCompatActivity {

    private ListView listViewOpiniones;
    private ArrayList<String> opinionesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_opiniones);

        listViewOpiniones = findViewById(R.id.listViewOpiniones);
        opinionesList = new ArrayList<>();

        // Llamar al método para cargar las opiniones
        cargarOpiniones();

        // Configurar el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opinionesList);
        listViewOpiniones.setAdapter(adapter);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void cargarOpiniones() {
        try {
            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM opinion", null);

            if (cursor.moveToFirst()) {
                do {
                    String categoria = cursor.getString(cursor.getColumnIndex("categoria"));
                    String opinion = cursor.getString(cursor.getColumnIndex("opinion"));
                    float calificacion = cursor.getFloat(cursor.getColumnIndex("calificacion"));

                    // Formato para mostrar la opinión en el ListView
                    String opinionTexto = "Categoría: " + categoria + "\nOpinión: " + opinion + "\nCalificación: " + calificacion;
                    opinionesList.add(opinionTexto);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar opiniones: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
