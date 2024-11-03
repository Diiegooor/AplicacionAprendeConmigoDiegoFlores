package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class hemisferionorteysur extends AppCompatActivity {

    private TextView tvDescripcion;
    private String[] lugares = {"Ecuador", "Australia", "Canadá", "Argentina", "Japón", "Sudáfrica"};
    private String lugarActual;
    private int indiceLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hemisferionorteysur);

        // Configuración para ajustar el diseño a los insets de la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botón de regresar
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Configuración del WebView para mostrar un video de introducción (opcional)
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript para el iframe
        webView.setWebViewClient(new WebViewClient());
        String videoID = "xLGIz6OVxkk";
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        // TextView para mostrar el lugar actual que el usuario debe clasificar
        tvDescripcion = findViewById(R.id.tv_descripcion);

        // Botones para clasificar los lugares
        Button btnHemisferioNorte = findViewById(R.id.btnHemisferioNorte);
        Button btnHemisferioSur = findViewById(R.id.btnHemisferioSur);

        // Inicialización del juego
        indiceLugar = 0;
        mostrarLugar();

        // Asignar comportamientos a los botones de clasificación
        btnHemisferioNorte.setOnClickListener(v -> verificarRespuesta("Norte"));
        btnHemisferioSur.setOnClickListener(v -> verificarRespuesta("Sur"));
    }

    // Método para mostrar el lugar actual en el TextView
    private void mostrarLugar() {
        lugarActual = lugares[indiceLugar];
        tvDescripcion.setText("¿" + lugarActual + " está en el hemisferio norte o sur?");
    }

    // Método para verificar si la respuesta seleccionada es correcta
    private void verificarRespuesta(String hemisferioSeleccionado) {
        // Definición de lugares en el hemisferio norte
        boolean esNorte = lugarActual.equals("Canadá") || lugarActual.equals("Japón") || lugarActual.equals("Ecuador");
        boolean respuestaCorrecta = (esNorte && hemisferioSeleccionado.equals("Norte")) ||
                (!esNorte && hemisferioSeleccionado.equals("Sur"));

        if (respuestaCorrecta) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
        }

        // Avanzar al siguiente lugar en la lista
        indiceLugar = (indiceLugar + 1) % lugares.length;
        mostrarLugar();
    }
}
