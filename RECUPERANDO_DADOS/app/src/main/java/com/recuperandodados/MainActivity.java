package com.recuperandodados;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private int digitado;
    private EditText etNumero;
    private TextView tvMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNumero = findViewById(R.id.et_numero);
        tvMensagem = findViewById(R.id.tv_mensagem);

        if (savedInstanceState != null && savedInstanceState.containsKey("NUMERO")) {
            Objeto objeto = (Objeto) savedInstanceState.getSerializable("NUMERO");
            int numero = objeto.numero;
            tvMensagem.setText("Eu memorizei: " + numero);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!etNumero.getText().toString().isEmpty()) {
            int numero = Integer.parseInt(etNumero.getText().toString());
            Objeto objeto = new Objeto();
            objeto.numero = numero;
            outState.putSerializable("NUMERO", objeto);
        }

    }

    private class Objeto implements Serializable {
        int numero;
    }
}