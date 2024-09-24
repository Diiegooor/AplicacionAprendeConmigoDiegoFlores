package com.example.aprendeconmigo1;

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opinion = opinionText.getText().toString();
                float rating = ratingBar.getRating();
                String category = opinionSpinner.getSelectedItem().toString();

                Toast.makeText(opinion1.this,
                        "Categoría: " + category + "\nOpinión: " + opinion + "\nCalificación: " + rating,
                        Toast.LENGTH_LONG).show();
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
}