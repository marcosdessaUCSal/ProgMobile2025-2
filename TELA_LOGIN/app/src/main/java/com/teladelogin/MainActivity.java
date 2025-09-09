package com.teladelogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.teladelogin.model.InfoLogin;

public class MainActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etSenha;
    private CheckBox cbConectado;

    private InfoLogin infoLogin;

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

        etLogin = findViewById(R.id.et_login);
        etSenha = findViewById(R.id.et_senha);
        cbConectado = findViewById(R.id.cb_conectado);

        infoLogin = new InfoLogin("aluno", "123456");
    }

    public void btnTentarEnviar(View view) {
        String login = etLogin.getText().toString();
        String senha = etSenha.getText().toString();
        if (login.equals(infoLogin.login) && senha.equals(infoLogin.senha)) {
            infoLogin.lembrar = cbConectado.isChecked();
            Intent intent = new Intent(this, ResultadosActivity.class);
            intent.putExtra("infoLogin", infoLogin);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Login ou senha incorretos",
                    Toast.LENGTH_SHORT).show();
            infoLogin.acrescentarErro();
        }
    }

}