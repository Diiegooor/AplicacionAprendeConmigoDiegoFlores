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
import java.util.Random;

public class abecedario extends AppCompatActivity {

    private TextView targetLetterText;
    private TextView resultText;
    private Button[] optionButtons = new Button[4];
    private char correctLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_abecedario);

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

        // Hacer que el WebView cargue el contenido dentro de la app en lugar de abrir el navegador
        webView.setWebViewClient(new WebViewClient());

        // Reemplazar con el ID del video de YouTube que proporcionaste
        String videoID = "1yZg9Atq4CM";

        // HTML content with YouTube iframe
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";

        // Cargar el iframe en el WebView
        webView.loadData(html, "text/html", "utf-8");

        // Inicializar vistas para el juego
        targetLetterText = findViewById(R.id.target_letter);
        resultText = findViewById(R.id.result_text);
        optionButtons[0] = findViewById(R.id.option_1);
        optionButtons[1] = findViewById(R.id.option_2);
        optionButtons[2] = findViewById(R.id.option_3);
        optionButtons[3] = findViewById(R.id.option_4);

        // Configurar el juego al iniciar
        setupGame();

        // Configurar escuchadores para los botones de opciones
        for (Button button : optionButtons) {
            button.setOnClickListener(this::checkAnswer);
        }
    }

    private void setupGame() {
        // Generar una letra aleatoria como objetivo
        correctLetter = (char) ('A' + new Random().nextInt(26));
        targetLetterText.setText(String.valueOf(correctLetter));

        // Configurar letras de opciones
        for (int i = 0; i < optionButtons.length; i++) {
            char optionLetter = (char) ('A' + new Random().nextInt(26));
            optionButtons[i].setText(String.valueOf(optionLetter));
        }

        // Colocar la letra correcta en una posición aleatoria
        optionButtons[new Random().nextInt(4)].setText(String.valueOf(correctLetter));

        // Limpiar el mensaje de resultado
        resultText.setText("");
    }

    private void checkAnswer(View view) {
        Button clickedButton = (Button) view;
        char selectedLetter = clickedButton.getText().toString().charAt(0);

        if (selectedLetter == correctLetter) {
            resultText.setText("¡Correcto!");
            Toast.makeText(this, "¡Bien hecho!", Toast.LENGTH_SHORT).show();
            setupGame();  // Iniciar un nuevo juego
        } else {
            resultText.setText("Inténtalo de nuevo");
        }
    }
}