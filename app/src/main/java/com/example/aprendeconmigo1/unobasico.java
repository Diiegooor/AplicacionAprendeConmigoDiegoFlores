package com.example.aprendeconmigo1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class unobasico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_unobasico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView myTextView = findViewById(R.id.aprendeVocales);
        myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(unobasico.this, vocales.class);
                startActivity(intent);
            }
        });

        TextView myTextView2 = findViewById(R.id.aprenderAbc);
        myTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(unobasico.this, abecedario.class);
                startActivity(intent);
            }
        });

        TextView myTextView3 = findViewById(R.id.aprenderNum);
        myTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(unobasico.this, numeros1a10.class);
                startActivity(intent);
            }
        });

        TextView myTextView4 = findViewById(R.id.aprenderSumar);
        myTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(unobasico.this, Sumar.class);
                startActivity(intent);
            }
        });

        TextView myTextView5 = findViewById(R.id.aprenderSeresVivos);
        myTextView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(unobasico.this, SeresVivos.class);
                startActivity(intent);
            }
        });

        TextView myTextView6 = findViewById(R.id.aprenderEtapasSeresVivos);
        myTextView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(unobasico.this, EtapasSeresVivos.class);
                startActivity(intent);
            }
        });
    }
}