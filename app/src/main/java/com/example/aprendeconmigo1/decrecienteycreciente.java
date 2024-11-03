package com.example.aprendeconmigo1;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class decrecienteycreciente extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button btnVerificar;
    private List<Integer> numbers;
    private List<Button> selectedButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_decrecienteycreciente);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        gridLayout = findViewById(R.id.gridLayout);
        btnVerificar = findViewById(R.id.btnVerificar);
        selectedButtons = new ArrayList<>();

        btnBack.setOnClickListener(v -> finish());

        // Iniciar el WebView para cargar el video de YouTube
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        String videoID = "zlKVueOJjSw";
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        // Inicializar y mostrar números
        initializeNumbers();

        // Configurar el botón de verificación
        btnVerificar.setOnClickListener(v -> verifyOrder());
    }

    private void initializeNumbers() {
        numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        for (Integer number : numbers) {
            Button numberButton = new Button(this);
            numberButton.setText(number.toString());
            numberButton.setOnClickListener(new NumberButtonClickListener(numberButton));
            gridLayout.addView(numberButton);
        }
    }

    private void verifyOrder() {
        List<Integer> currentOrder = new ArrayList<>();

        // Obtener los números seleccionados
        for (Button button : selectedButtons) {
            currentOrder.add(Integer.parseInt(button.getText().toString()));
        }

        // Crear una lista ordenada de los números
        List<Integer> sortedOrder = new ArrayList<>(numbers);
        Collections.sort(sortedOrder);

        // Comparar el orden actual con el orden correcto
        if (currentOrder.equals(sortedOrder)) {
            Toast.makeText(this, "¡Correcto! Los números están en orden creciente.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Intenta de nuevo. Los números no están en el orden correcto.", Toast.LENGTH_SHORT).show();
        }

        // Limpiar la selección después de verificar
        selectedButtons.clear();
    }

    private class NumberButtonClickListener implements View.OnClickListener {
        private final Button button;

        public NumberButtonClickListener(Button button) {
            this.button = button;
        }

        @Override
        public void onClick(View v) {
            // Alternar la selección del botón
            if (selectedButtons.contains(button)) {
                selectedButtons.remove(button);
                button.setBackgroundColor(0x00000000); // Resetear el color
            } else {
                selectedButtons.add(button);
                button.setBackgroundColor(0xFF00FF00); // Cambiar color a verde
            }
        }
    }
}



