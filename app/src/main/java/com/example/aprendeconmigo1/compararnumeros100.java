package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class compararnumeros100 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compararnumeros100);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Iniciar el WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript para que funcione el iframe

        // Hacer que el WebView cargue el contenido dentro de la app en lugar de abrir el navegador
        webView.setWebViewClient(new WebViewClient());

        // Reemplazar con el ID del video de YouTube que proporcionaste
        String videoID = "T7lMKq3JR18";

        // HTML content with YouTube iframe
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";

        // Cargar el iframe en el WebView
        webView.loadData(html, "text/html", "utf-8");

    }
}