package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

import java.util.ArrayList;
import java.util.Arrays;

public class vocales extends AppCompatActivity {

    private Button btnVerificar;
    private Button btnReiniciar;
    private ArrayList<String> vocalesSeleccionadas;
    private final String[] vocalesCorrectas = {"A", "E", "I", "O", "U"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Configurar EdgeToEdge según tu implementación actual
        setContentView(R.layout.activity_vocales); // Asumiendo que tu layout es 'activity_vocales.xml'

        // Configuración para ajustar insets en el layout principal
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
        String videoID = "CqTXFbnG0ag";

        // HTML content with YouTube iframe
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";

        // Cargar el iframe en el WebView
        webView.loadData(html, "text/html", "utf-8");

        // Inicializar elementos del juego
        btnVerificar = findViewById(R.id.btn_verificar);
        btnReiniciar = findViewById(R.id.btn_reiniciar);
        vocalesSeleccionadas = new ArrayList<>();

        // Configurar botones de vocales
        setUpVocalButton(R.id.btn_vocal_a);
        setUpVocalButton(R.id.btn_vocal_e);
        setUpVocalButton(R.id.btn_vocal_i);
        setUpVocalButton(R.id.btn_vocal_o);
        setUpVocalButton(R.id.btn_vocal_u);

        // Botón de verificar
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarOrden();
            }
        });

        // Botón de reiniciar
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuego();
            }
        });
    }

    private void setUpVocalButton(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vocal = button.getText().toString();
                vocalesSeleccionadas.add(vocal);
                button.setEnabled(false); // Desactivar botón después de seleccionarlo
            }
        });
    }

    private void verificarOrden() {
        if (vocalesSeleccionadas.size() < 5) {
            Toast.makeText(this, "Selecciona todas las vocales", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vocalesSeleccionadas.equals(Arrays.asList(vocalesCorrectas))) {
            Toast.makeText(this, "¡Correcto! Las vocales están en orden.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrecto. Intenta nuevamente.", Toast.LENGTH_SHORT).show();
            darPista();
        }
    }

    private void darPista() {
        // Por simplicidad, podemos mostrar la lista correcta como pista
        StringBuilder pista = new StringBuilder("Orden correcto: ");
        for (String vocal : vocalesCorrectas) {
            pista.append(vocal).append(" ");
        }
        Toast.makeText(this, pista.toString(), Toast.LENGTH_LONG).show();
    }

    private void reiniciarJuego() {
        vocalesSeleccionadas.clear();
        // Reactivar todos los botones de vocales
        for (int id : new int[]{R.id.btn_vocal_a, R.id.btn_vocal_e, R.id.btn_vocal_i, R.id.btn_vocal_o, R.id.btn_vocal_u}) {
            Button button = findViewById(id);
            button.setEnabled(true);
        }
    }
}

