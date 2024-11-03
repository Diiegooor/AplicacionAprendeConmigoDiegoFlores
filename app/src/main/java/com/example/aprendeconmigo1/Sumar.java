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

import java.util.Random;

public class Sumar extends AppCompatActivity {

    private TextView number1Text, number2Text, resultText;
    private Button[] optionButtons = new Button[3];
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sumar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar los elementos de la interfaz
        number1Text = findViewById(R.id.number1_text);
        number2Text = findViewById(R.id.number2_text);
        resultText = findViewById(R.id.result_text);
        optionButtons[0] = findViewById(R.id.option_1);
        optionButtons[1] = findViewById(R.id.option_2);
        optionButtons[2] = findViewById(R.id.option_3);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Inicializar el WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript para que funcione el iframe
        webView.setWebViewClient(new WebViewClient());

        // Reemplazar con el ID del video de YouTube que proporcionaste
        String videoID = "br4Z-HZtIQQ";
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        setupGame();

        for (Button button : optionButtons) {
            button.setOnClickListener(this::checkAnswer);
        }
    }

    private void setupGame() {
        Random random = new Random();

        // Generar dos números aleatorios entre 1 y 10
        int number1 = random.nextInt(10) + 1;
        int number2 = random.nextInt(10) + 1;
        correctAnswer = number1 + number2;

        // Mostrar los números en la interfaz
        number1Text.setText(String.valueOf(number1));
        number2Text.setText(String.valueOf(number2));

        // Configurar opciones de respuesta
        int correctPosition = random.nextInt(3);
        optionButtons[correctPosition].setText(String.valueOf(correctAnswer));

        for (int i = 0; i < optionButtons.length; i++) {
            if (i != correctPosition) {
                int randomOption;
                do {
                    randomOption = random.nextInt(19) + 2; // Sumas posibles entre 2 y 20
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
