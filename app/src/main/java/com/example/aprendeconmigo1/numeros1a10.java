package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class numeros1a10 extends AppCompatActivity {

    private LinearLayout objectContainer;
    private TextView resultText;
    private Button[] optionButtons = new Button[3];
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_numeros1a10);

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
        String videoID = "IZ01MMMV3ak";

        // HTML content with YouTube iframe
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";

        // Cargar el iframe en el WebView
        webView.loadData(html, "text/html", "utf-8");

        // Integración del juego de contar números
        objectContainer = findViewById(R.id.object_container);
        resultText = findViewById(R.id.result_text);
        optionButtons[0] = findViewById(R.id.option_1);
        optionButtons[1] = findViewById(R.id.option_2);
        optionButtons[2] = findViewById(R.id.option_3);

        setupGame();

        for (Button button : optionButtons) {
            button.setOnClickListener(this::checkAnswer);
        }
    }

    private void setupGame() {
        // Limpiar contenedor de objetos
        objectContainer.removeAllViews();

        // Generar una cantidad aleatoria de objetos
        correctAnswer = new Random().nextInt(10) + 1;

        // Agregar objetos al contenedor
        for (int i = 0; i < correctAnswer; i++) {
            View objectView = new View(this);
            objectView.setBackgroundResource(android.R.drawable.star_on); // Cambia a cualquier ícono que prefieras
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(10, 0, 10, 0);
            objectView.setLayoutParams(params);
            objectContainer.addView(objectView);
        }

        // Configurar opciones de respuesta
        int correctPosition = new Random().nextInt(3);
        optionButtons[correctPosition].setText(String.valueOf(correctAnswer));

        for (int i = 0; i < optionButtons.length; i++) {
            if (i != correctPosition) {
                int randomOption;
                do {
                    randomOption = new Random().nextInt(10) + 1;
                } while (randomOption == correctAnswer);
                optionButtons[i].setText(String.valueOf(randomOption));
            }
        }

        // Limpiar el mensaje de resultado
        resultText.setText("");
    }

    private void checkAnswer(View view) {
        Button clickedButton = (Button) view;
        int selectedNumber = Integer.parseInt(clickedButton.getText().toString());

        if (selectedNumber == correctAnswer) {
            resultText.setText("¡Correcto!");
            Toast.makeText(this, "¡Bien hecho!", Toast.LENGTH_SHORT).show();
            setupGame();  // Iniciar un nuevo juego
        } else {
            resultText.setText("Inténtalo de nuevo");
        }
    }
}