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

public class fotosintesis extends AppCompatActivity {

    private WebView webView;
    private TextView tvOrganismo;
    private Button btnSi, btnNo;
    private List<Organismo> organismos;
    private Organismo currentOrganismo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fotosintesis);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        tvOrganismo = findViewById(R.id.tv_organismo);
        btnSi = findViewById(R.id.btn_si);
        btnNo = findViewById(R.id.btn_no);
        webView = findViewById(R.id.webview);

        btnBack.setOnClickListener(v -> finish());

        // Iniciar el WebView para cargar el video de YouTube
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // ID del video de YouTube
        String videoID = "WYqLVxSNdAE";
        String html = "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" + videoID + "\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>";

        webView.loadData(html, "text/html", "utf-8");

        // Inicializar la lista de organismos
        initializeOrganismos();
        showNextOrganismo();

        // Configurar los botones de respuesta
        btnSi.setOnClickListener(v -> checkAnswer(true));
        btnNo.setOnClickListener(v -> checkAnswer(false));
    }

    private void initializeOrganismos() {
        organismos = new ArrayList<>();
        organismos.add(new Organismo("Planta", true));
        organismos.add(new Organismo("Perro", false));
        organismos.add(new Organismo("Alga", true));
        organismos.add(new Organismo("Hongo", false));
        organismos.add(new Organismo("Cyanobacteria", true));
        Collections.shuffle(organismos); // Barajar la lista de organismos
    }

    private void showNextOrganismo() {
        if (!organismos.isEmpty()) {
            currentOrganismo = organismos.remove(0); // Obtener y eliminar el organismo actual
            tvOrganismo.setText(currentOrganismo.getNombre());
        } else {
            tvOrganismo.setText("¡Has terminado!");
            btnSi.setEnabled(false);
            btnNo.setEnabled(false);
        }
    }

    private void checkAnswer(boolean answer) {
        if (answer == currentOrganismo.isFotosintesis()) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrecto. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
        }
        showNextOrganismo(); // Mostrar el siguiente organismo
    }

    private static class Organismo {
        private final String nombre;
        private final boolean fotosintesis;

        public Organismo(String nombre, boolean fotosintesis) {
            this.nombre = nombre;
            this.fotosintesis = fotosintesis;
        }

        public String getNombre() {
            return nombre;
        }

        public boolean isFotosintesis() {
            return fotosintesis;
        }
    }
}
