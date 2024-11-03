package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class nivelestroficos extends AppCompatActivity {

    private int currentQuestionIndex = 0; // Índice de la pregunta actual
    private List<Question> questions; // Lista de preguntas
    private TextView tvPregunta; // TextView para mostrar la pregunta
    private TextView tvResultado; // TextView para mostrar resultados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nivelestroficos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Inicializar el WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript
        webView.setWebViewClient(new WebViewClient());

        // ID del video de YouTube
        String videoID = "i7x-9bBbuYE";
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        // Inicializar botones de respuesta y resultados
        Button btnRespuesta1 = findViewById(R.id.btnRespuesta1);
        Button btnRespuesta2 = findViewById(R.id.btnRespuesta2);
        Button btnRespuesta3 = findViewById(R.id.btnRespuesta3);
        tvPregunta = findViewById(R.id.tv_pregunta); // Inicializar TextView para la pregunta
        tvResultado = findViewById(R.id.tv_resultado);
        Button btnNext = findViewById(R.id.btnNext);

        // Crear preguntas
        createQuestions();
        loadQuestion(); // Cargar la primera pregunta

        // Configurar botones de respuesta
        btnRespuesta1.setOnClickListener(v -> checkAnswer(1));
        btnRespuesta2.setOnClickListener(v -> checkAnswer(2));
        btnRespuesta3.setOnClickListener(v -> checkAnswer(3));

        // Configurar botón Siguiente
        btnNext.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                loadQuestion();
                tvResultado.setText("");
                btnNext.setVisibility(View.GONE); // Ocultar el botón de siguiente
            } else {
                tvResultado.setText("¡Juego terminado! Has respondido todas las preguntas.");
                btnNext.setVisibility(View.GONE);
                // Aquí puedes agregar lógica para reiniciar o finalizar el juego
            }
        });
    }

    private void createQuestions() {
        questions = new ArrayList<>();
        // Agregar preguntas sobre niveles tróficos
        questions.add(new Question("¿Cuál es el primer nivel trófico?", "Productores", "Consumidores primarios", "Descomponedores", 1));
        questions.add(new Question("¿Qué organismo ocupa el segundo nivel trófico?", "Productores", "Consumidores primarios", "Consumidores secundarios", 2));
        questions.add(new Question("¿Qué son los descomponedores?", "Organismos que producen energía", "Organismos que descomponen materia orgánica", "Organismos que consumen plantas", 2));
        // Agrega más preguntas según sea necesario
    }

    private void loadQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        tvPregunta.setText(currentQuestion.questionText);
        ((Button) findViewById(R.id.btnRespuesta1)).setText(currentQuestion.option1);
        ((Button) findViewById(R.id.btnRespuesta2)).setText(currentQuestion.option2);
        ((Button) findViewById(R.id.btnRespuesta3)).setText(currentQuestion.option3);
        tvResultado.setText(""); // Limpiar el resultado
        findViewById(R.id.btnNext).setVisibility(View.GONE); // Ocultar botón Siguiente
    }

    private void checkAnswer(int selectedAnswer) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (selectedAnswer == currentQuestion.correctOption) {
            tvResultado.setText("¡Correcto!");
        } else {
            tvResultado.setText("Incorrecto. Inténtalo de nuevo.");
        }
        findViewById(R.id.btnNext).setVisibility(View.VISIBLE); // Mostrar botón Siguiente
    }

    // Clase interna para representar una pregunta
    static class Question {
        String questionText;
        String option1;
        String option2;
        String option3;
        int correctOption;

        Question(String questionText, String option1, String option2, String option3, int correctOption) {
            this.questionText = questionText;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.correctOption = correctOption;
        }
    }
}


