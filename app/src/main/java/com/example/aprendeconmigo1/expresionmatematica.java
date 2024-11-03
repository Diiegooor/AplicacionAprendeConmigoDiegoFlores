package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class expresionmatematica extends AppCompatActivity {

    private TextView tvExpression;
    private EditText etAnswer;
    private Button btnCheckAnswer;
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expresionmatematica);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        tvExpression = findViewById(R.id.tv_expression);
        etAnswer = findViewById(R.id.et_answer);
        btnCheckAnswer = findViewById(R.id.btn_check_answer);

        btnBack.setOnClickListener(v -> finish());

        // Iniciar el WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript para que funcione el iframe

        // Hacer que el WebView cargue el contenido dentro de la app en lugar de abrir el navegador
        webView.setWebViewClient(new WebViewClient());

        // ID del video de YouTube
        String videoID = "_WU0nL7XYIs";

        // HTML content with YouTube iframe
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";

        // Cargar el iframe en el WebView
        webView.loadData(html, "text/html", "utf-8");

        // Generar una expresión matemática aleatoria
        generateMathExpression();

        // Configurar el botón de verificación
        btnCheckAnswer.setOnClickListener(v -> checkAnswer());
    }

    private void generateMathExpression() {
        Random random = new Random();
        int num1 = random.nextInt(10) + 1; // Número entre 1 y 10
        int num2 = random.nextInt(10) + 1; // Número entre 1 y 10

        // Elegir aleatoriamente entre suma y resta
        if (random.nextBoolean()) {
            tvExpression.setText(num1 + " + " + num2);
            correctAnswer = num1 + num2;
        } else {
            tvExpression.setText(num1 + " - " + num2);
            correctAnswer = num1 - num2;
        }
    }

    private void checkAnswer() {
        String userInput = etAnswer.getText().toString();
        if (userInput.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa una respuesta", Toast.LENGTH_SHORT).show();
            return;
        }

        int userAnswer = Integer.parseInt(userInput);
        if (userAnswer == correctAnswer) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            generateMathExpression(); // Generar una nueva expresión
            etAnswer.setText(""); // Limpiar el campo de respuesta
        } else {
            Toast.makeText(this, "Incorrecto. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
        }
    }
}
