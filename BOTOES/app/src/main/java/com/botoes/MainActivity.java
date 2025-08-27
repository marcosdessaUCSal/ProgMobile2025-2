package com.botoes;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.botoes.model.Botoes;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Botoes botoes;
    private final String COR_SELECT = "#FFC107";
    private final String COR_UNSELECT = "#44000000";
    private LayoutInflater inflater;
    private TableLayout tableLayout;
    private final HashMap<Integer, Button> btnMap = new HashMap<>();

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

        inflater = LayoutInflater.from(this);
        tableLayout = findViewById(R.id.table_layout);

        // criando os botões
        botoes = new Botoes();  // lógica
        criarBotoes();          // interface
    }

    private void criarBotoes() {
        int btnId = 0;
        for (int lin = 0; lin < Botoes.NUM_LINHAS; lin++) {
            TableRow tableRow = new TableRow(this);

            for (int col = 0; col < Botoes.NUM_COLUNAS; col++) {
                Button btn = (Button) inflater.inflate(R.layout.btn_template,
                        tableRow, false);
                btn.setId(btnId);
                tableRow.addView(btn);
                // definindo um listener para o botão
                btn.setOnClickListener(view -> {
                    onClickBtn(view);
                });
                // armazenando referência para os botões
                btnMap.put(btnId, btn);
                btnId++;
            }
            tableLayout.addView(tableRow);
        }
        sync();
    }

    // sincronização: correspondência -- views vs classe Botoes
    private void sync() {
        for (int id = 0; id < btnMap.size(); id++) {
            Button btn = btnMap.get(id);
            if (botoes.aceso(id)) {
                btn.setBackgroundColor(Color.parseColor(COR_SELECT));
            } else {
                btn.setBackgroundColor(Color.parseColor(COR_UNSELECT));
            }
        }
    }

    /************************************
        INTERAÇÃO COM O USUÁRIO: CLICKS
     ************************************/

    // Para alterar a cor do quadradinho (clicar no botão)
    private void onClickBtn(View v) {
        int id = v.getId();
        botoes.click(id);
        sync();
    }

    // Para o botão INVERTER
    public void onClickInverter(View v) {
        botoes.inverter();
        sync();
    }

    // Para o botão RESET
    public void onClickReset(View v) {
        botoes.reset();
        sync();
    }
}