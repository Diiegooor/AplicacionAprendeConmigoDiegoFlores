package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class vocales extends AppCompatActivity {

    private Button btnVerificar;
    private Button btnReiniciar;
    private ArrayList<String> vocalesSeleccionadas;
    private ArrayList<String> vocalesDisponibles;  // Lista para las vocales que aparecen en los botones
    private final String[] vocalesCorrectas = {"A", "E", "I", "O", "U"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocales); // Asumiendo que tu layout es 'activity_vocales.xml'

        // Configuración para ajustar insets en el layout principal
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

        // Iniciar el WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript para que funcione el iframe
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

        // Inicializar las vocales disponibles y el array de vocales seleccionadas
        vocalesDisponibles = new ArrayList<>(Arrays.asList(vocalesCorrectas));
        vocalesSeleccionadas = new ArrayList<>();

        // Inicializar los botones de vocales
        setUpVocalButton(R.id.btn_vocal_a);
        setUpVocalButton(R.id.btn_vocal_e);
        setUpVocalButton(R.id.btn_vocal_i);
        setUpVocalButton(R.id.btn_vocal_o);
        setUpVocalButton(R.id.btn_vocal_u);

        // Inicializar botones de verificación y reinicio
        btnVerificar = findViewById(R.id.btn_verificar);
        btnReiniciar = findViewById(R.id.btn_reiniciar);

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

        // Mezclar las vocales al iniciar
        mezclarVocales();
        actualizarBotones();
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
        StringBuilder pista = new StringBuilder("Orden correcto: ");
        for (String vocal : vocalesCorrectas) {
            pista.append(vocal).append(" ");
        }
        Toast.makeText(this, pista.toString(), Toast.LENGTH_LONG).show();
    }

    private void reiniciarJuego() {
        vocalesSeleccionadas.clear();
        // Mezclar las vocales nuevamente al reiniciar
        mezclarVocales();
        actualizarBotones();

        // Reactivar todos los botones de vocales
        for (int id : new int[]{R.id.btn_vocal_a, R.id.btn_vocal_e, R.id.btn_vocal_i, R.id.btn_vocal_o, R.id.btn_vocal_u}) {
            Button button = findViewById(id);
            button.setEnabled(true);
        }
    }

    private void mezclarVocales() {
        Collections.shuffle(vocalesDisponibles); // Mezcla aleatoriamente las vocales
    }

    private void actualizarBotones() {
        // Asignar el texto de las vocales mezcladas a los botones
        Button btnVocalA = findViewById(R.id.btn_vocal_a);
        Button btnVocalE = findViewById(R.id.btn_vocal_e);
        Button btnVocalI = findViewById(R.id.btn_vocal_i);
        Button btnVocalO = findViewById(R.id.btn_vocal_o);
        Button btnVocalU = findViewById(R.id.btn_vocal_u);

        btnVocalA.setText(vocalesDisponibles.get(0));
        btnVocalE.setText(vocalesDisponibles.get(1));
        btnVocalI.setText(vocalesDisponibles.get(2));
        btnVocalO.setText(vocalesDisponibles.get(3));
        btnVocalU.setText(vocalesDisponibles.get(4));
    }
}
