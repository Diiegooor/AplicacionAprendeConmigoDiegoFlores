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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class hiatosydiptongos extends AppCompatActivity {

    private TextView tvPalabra, tvResultado;
    private List<String> palabrasHiatos, palabrasDiptongos;
    private String palabraActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hiatosydiptongos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Configurar el WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String videoID = "BadUITZcP3E";
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        // Configuración del juego
        tvPalabra = findViewById(R.id.tv_palabra);
        tvResultado = findViewById(R.id.tv_resultado);

        // Inicializar listas de palabras
        palabrasHiatos = new ArrayList<>();
        palabrasDiptongos = new ArrayList<>();

        // Agregar ejemplos
        palabrasHiatos.add("poeta");
        palabrasHiatos.add("caos");
        palabrasDiptongos.add("aire");
        palabrasDiptongos.add("ciudad");

        // Mezclar y mostrar una palabra al inicio
        mostrarNuevaPalabra();

        // Configurar botones de elección
        Button btnHiato = findViewById(R.id.btn_hiato);
        Button btnDiptongo = findViewById(R.id.btn_diptongo);

        btnHiato.setOnClickListener(v -> verificarRespuesta("hiato"));
        btnDiptongo.setOnClickListener(v -> verificarRespuesta("diptongo"));
    }

    // Método para mostrar una nueva palabra aleatoria
    private void mostrarNuevaPalabra() {
        List<String> todasLasPalabras = new ArrayList<>();
        todasLasPalabras.addAll(palabrasHiatos);
        todasLasPalabras.addAll(palabrasDiptongos);
        Collections.shuffle(todasLasPalabras);
        palabraActual = todasLasPalabras.get(0);
        tvPalabra.setText(palabraActual);
        tvResultado.setText("");
    }

    // Método para verificar la respuesta del usuario
    private void verificarRespuesta(String respuesta) {
        if ((palabrasHiatos.contains(palabraActual) && respuesta.equals("hiato")) ||
                (palabrasDiptongos.contains(palabraActual) && respuesta.equals("diptongo"))) {
            tvResultado.setText("¡Correcto!");
            Toast.makeText(this, "¡Bien hecho!", Toast.LENGTH_SHORT).show();
        } else {
            tvResultado.setText("Incorrecto. Inténtalo de nuevo.");
        }

        // Mostrar una nueva palabra después de la respuesta
        mostrarNuevaPalabra();
    }
}
