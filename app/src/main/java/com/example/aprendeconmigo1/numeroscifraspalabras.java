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

import java.util.HashMap;
import java.util.Map;

public class numeroscifraspalabras extends AppCompatActivity {

    private TextView tvNumberPrompt, tvResult;
    private EditText etUserInput;
    private Button btnCheck;
    private Map<String, String> numberMap;
    private String currentNumberWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_numeroscifraspalabras);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración del botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Configuración del WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript para que funcione el iframe
        webView.setWebViewClient(new WebViewClient());

        // Reemplazar con el ID del video de YouTube que proporcionaste
        String videoID = "tzlxqLZmW1o";
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        // Configuración del juego de escribir en cifras o palabras
        tvNumberPrompt = findViewById(R.id.tv_number_prompt);
        etUserInput = findViewById(R.id.et_user_input);
        btnCheck = findViewById(R.id.btn_check);
        tvResult = findViewById(R.id.tv_result);

        // Inicializar el mapa de números en palabras
        initializeNumberMap();
        generateNewNumberPrompt();

        // Configuración del botón de comprobar
        btnCheck.setOnClickListener(v -> checkAnswer());
    }

    private void initializeNumberMap() {
        numberMap = new HashMap<>();
        numberMap.put("1", "uno");
        numberMap.put("2", "dos");
        numberMap.put("3", "tres");
        numberMap.put("4", "cuatro");
        numberMap.put("5", "cinco");
        numberMap.put("6", "seis");
        numberMap.put("7", "siete");
        numberMap.put("8", "ocho");
        numberMap.put("9", "nueve");
        numberMap.put("10", "diez");
        // Puedes agregar más números si lo deseas
    }

    private void generateNewNumberPrompt() {
        // Selecciona un número aleatorio del mapa
        Object[] keys = numberMap.keySet().toArray();
        String randomKey = (String) keys[(int) (Math.random() * keys.length)];
        currentNumberWord = numberMap.get(randomKey);

        // Muestra el número en cifras o palabras de manera aleatoria
        if (Math.random() > 0.5) {
            tvNumberPrompt.setText(randomKey); // Muestra el número en cifras
        } else {
            tvNumberPrompt.setText(currentNumberWord); // Muestra el número en palabras
        }
    }

    private void checkAnswer() {
        String userAnswer = etUserInput.getText().toString().trim().toLowerCase();
        String correctAnswer = tvNumberPrompt.getText().toString().matches("\\d+")
                ? currentNumberWord
                : getKeyByValue(numberMap, currentNumberWord);

        if (userAnswer.equals(correctAnswer)) {
            tvResult.setText("¡Correcto!");
            tvResult.setVisibility(View.VISIBLE);
            generateNewNumberPrompt(); // Genera un nuevo número tras cada respuesta correcta
        } else {
            tvResult.setText("Intenta nuevamente");
            tvResult.setVisibility(View.VISIBLE);
        }
    }

    private String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
