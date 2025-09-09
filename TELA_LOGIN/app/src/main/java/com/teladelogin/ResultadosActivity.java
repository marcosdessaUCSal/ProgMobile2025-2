package com.teladelogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.teladelogin.model.InfoLogin;

public class ResultadosActivity extends AppCompatActivity {

    private TextView tvResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvResposta = findViewById(R.id.tv_resposta);

        Intent intent = getIntent();
        InfoLogin infoLogin = (InfoLogin) intent.getSerializableExtra("infoLogin");

        exibirResultados(infoLogin);
    }

    private void exibirResultados(InfoLogin info) {
        StringBuilder stb = new StringBuilder();
        stb.append("Login: ").append(info.login).append("\n")
                .append("Senha: ").append(info.senha).append("\n")
                .append("Errou senha/login ").append(info.erros).append(" vez(es)").append("\n");
        if (info.lembrar) {
            stb.append("-> Quer ser lembrado!");
        } else {
            stb.append("-> Não quer ser lembrado!");
        }
        tvResposta.setText(stb.toString());
    }
}

