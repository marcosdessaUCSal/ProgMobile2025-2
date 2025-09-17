package com.tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tracker.model.Registro;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // Constantes
    private static final String URL_BASE = "http://127.0.0.1:8080";
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final Gson gson = new Gson();
    private final OkHttpClient http = new OkHttpClient();

    // Área de registros
    private LinearLayout linLayoutRegistros;

    private EditText editTextCodigo;

    private LayoutInflater layoutInflater;

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

        linLayoutRegistros = findViewById(R.id.linLayoutRegistros);
        layoutInflater = LayoutInflater.from(this);
        editTextCodigo = findViewById(R.id.editTextCodigo);

    }

    /*
        === MÉTODOS PARA ATUALIZAR DADOS NA TELA ***
     */

    private void porRegistroNoLayout(ArrayList<Registro> lista) {
        editTextCodigo.setText("");
        boolean escuro = true;
        linLayoutRegistros.removeAllViews();
        for (int i = lista.size() - 1; i >= 0; i--) {
            LinearLayout layout;
            if (escuro) {
                layout = (LinearLayout) layoutInflater
                        .inflate(R.layout.template_registro_escuro, linLayoutRegistros, false);
            } else {
                layout = (LinearLayout) layoutInflater
                        .inflate(R.layout.template_registro_claro, linLayoutRegistros, false);
            }
            TextView tvData = layout.findViewById(R.id.textViewData);
            TextView tvLocal = layout.findViewById(R.id.textViewLocal);
            TextView tvDescr = layout.findViewById(R.id.textViewDescr);
            tvData.setText(lista.get(i).data);
            tvLocal.setText(lista.get(i).cidade);
            tvDescr.setText(lista.get(i).descr);
            linLayoutRegistros.addView(layout);
            escuro = !escuro;
        }
    }

    private void porMensagemNotFound() {
        ImageView imgView = (ImageView) layoutInflater
                .inflate(R.layout.template_not_found, linLayoutRegistros, false);
        linLayoutRegistros.removeAllViews();
        linLayoutRegistros.addView(imgView);
    }

    private void porMensagemDesculpas() {
        ImageView imgView = (ImageView) layoutInflater
                .inflate(R.layout.template_erro_servidor, linLayoutRegistros, false);
        linLayoutRegistros.removeAllViews();
        linLayoutRegistros.addView(imgView);
    }

    /*
        ==== PARA COMUNICAÇÃO COM A API + METODO ONCLICK ================
     */

    public void onClickEnviar(View v) {
        // Verificando o código digitado
        String codigo = editTextCodigo.getText().toString();
        codigo = codigo.toUpperCase();
        codigo = codigo.trim(); // elimina espaços no início e no fim
        if (codigo.isEmpty()) {
            editTextCodigo.setText("");
            return;
        }

        // Definindo a request
        String url = URL_BASE + "/pesquisar/" + codigo;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .build();

        http.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (Response res = response) {
                    String body = res.body() != null ? res.body().string() : "";

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Registro>>() {
                    }.getType();
                    ArrayList<Registro> lista = gson.fromJson(body, listType);

                    runOnUiThread(() -> {
                        if (!lista.isEmpty()) {
                            porRegistroNoLayout(lista);
                        } else {
                            porMensagemNotFound();
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    porMensagemDesculpas();
                });
            }
        });
    }

}