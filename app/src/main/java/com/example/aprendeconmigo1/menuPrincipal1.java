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
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class menuPrincipal1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal1);

        CardView cardView = findViewById(R.id.cardPrimero);
        CardView cardView1= findViewById(R.id.cardSegundo);
        CardView cardView2= findViewById(R.id.cardTercero);
        CardView cardView3= findViewById(R.id.cardCuarto);
        CardView cardView4= findViewById(R.id.cardQuinto);
        CardView cardView5= findViewById(R.id.cardSexto);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuPrincipal1.this, unobasico.class);
                startActivity(intent);
            }
        });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuPrincipal1.this, dosbasico.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuPrincipal1.this, tresbasico.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuPrincipal1.this, cuatrobasico.class);
                startActivity(intent);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuPrincipal1.this, cincobasico.class);
                startActivity(intent);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuPrincipal1.this, seisbasico.class);
                startActivity(intent);
            }
        });
        Button btnOpinion = findViewById(R.id.btnOpinion);
        btnOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuPrincipal1.this, opinion1.class);
                startActivity(intent);
            }
        });

    }
}