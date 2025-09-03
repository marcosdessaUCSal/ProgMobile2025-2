package com.ciclodevida;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ciclodevida.model.CicloDeVida;

public class MainActivity extends AppCompatActivity {

    private CicloDeVida cicloDeVida;
    private TextView tvConteudo;
    private int x;

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

        tvConteudo = findViewById(R.id.tv_conteudo);

        if (CicloDeVida.podeDizerValor()) {
            x = CicloDeVida.digaValor();
        }

        cicloDeVida = CicloDeVida.getInstance();
        cicloDeVida.registraEstado("onCreate, x = " + x);

        mostraConteudo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cicloDeVida.registraEstado("onDestroy, x = " + x);
        mostraConteudo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cicloDeVida.registraEstado("onStart, x = " + x);
        mostraConteudo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cicloDeVida.registraEstado("onStop, x = " + x);
        mostraConteudo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cicloDeVida.registraEstado("onPause, x = " + x);
        mostraConteudo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cicloDeVida.registraEstado("onResume, x = " + x);
        mostraConteudo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cicloDeVida.registraEstado("onRestart, x = " + x);
        mostraConteudo();
    }

    private void mostraConteudo() {
        tvConteudo.setText(cicloDeVida.toString());
    }
}