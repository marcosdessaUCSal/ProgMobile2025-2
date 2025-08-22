package com.example.appcalculadora;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcalculadora.model.Calculadora;
import com.example.appcalculadora.model.Op;

public class MainActivity extends AppCompatActivity {

    private Button btnDiv;
    private Button btnMult;
    private Button btnMenos;
    private Button btnMais;
    private TextView tvVisor;
    private TextView tvMiniVisor;

    private final String COR_ESPECIAL = "#337986CB";
    private final String COR_SELECT = "#FFE64A19";

    private int viewId;
    String nomeId;
    Calculadora calculadora;
    private Op op;

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

        tvVisor = findViewById(R.id.tv_visor);
        btnDiv = findViewById(R.id.btnD);
        btnMult = findViewById(R.id.btnX);
        btnMenos = findViewById(R.id.btnMenos);
        btnMais = findViewById(R.id.btnMais);
        tvMiniVisor = findViewById(R.id.tv_minivisor);

        calculadora = new Calculadora();
        mostraVisor();
        mostraMiniVisor();
    }


    // Mostra o conteúdo do visor
    private void mostraVisor() {
        tvVisor.setText(calculadora.mostraVisor());
    }

    // Mostra o conteúdo do pequeno visor
    private void mostraMiniVisor() {
        tvMiniVisor.setText(calculadora.getMinivisor());
    }

    // Gerencia cliques de botões
    public void onClickHandleBtns(View view) {
        viewId = view.getId();
        nomeId = getResources().getResourceEntryName(viewId);
        // Seleciona a reação correspondente à tecla pressionada
        switch (nomeId) {
            case "btnAC":
                calculadora.teclaC();
                break;
            case "btnQ":
                calculadora.quadrado();
                break;
            case "btnR":
                calculadora.raizQuadrada();
                break;
            case "btnD":
                calculadora.defineOperacao(Op.DIV);
                break;
            case "btn7":
                calculadora.insereDigito("7");
                break;
            case "btn8":
                calculadora.insereDigito("8");
                break;
            case "btn9":
                calculadora.insereDigito("9");
                break;
            case "btnX":
                calculadora.defineOperacao(Op.MULT);
                break;
            case "btn4":
                calculadora.insereDigito("4");
                break;
            case "btn5":
                calculadora.insereDigito("5");
                break;
            case "btn6":
                calculadora.insereDigito("6");
                break;
            case "btnMenos":
                calculadora.defineOperacao(Op.SUB);
                break;
            case "btn1":
                calculadora.insereDigito("1");
                break;
            case "btn2":
                calculadora.insereDigito("2");
                break;
            case "btn3":
                calculadora.insereDigito("3");
                break;
            case "btnMais":
                calculadora.defineOperacao(Op.SUM);
                break;
            case "btn0":
                calculadora.insereDigito("0");
                break;
            case "btnPt":
                calculadora.insereDigito(".");
                break;
            case "btnBK":
                calculadora.teclaBk();
                break;
            case "btnIgual":
                calculadora.igual();
                break;
        }
        mostraVisor();
        mostraMiniVisor();
        handleBtnCor();
    }

    // Gerencia os botões que mudam de cor (Mult, Div, Soma, Sub)
    private void handleBtnCor() {
        btnMais.setBackgroundColor(Color.parseColor(COR_ESPECIAL));
        btnMenos.setBackgroundColor(Color.parseColor(COR_ESPECIAL));
        btnDiv.setBackgroundColor(Color.parseColor(COR_ESPECIAL));
        btnMult.setBackgroundColor(Color.parseColor(COR_ESPECIAL));
        op = calculadora.getOp();
        switch (op) {
            case DIV:
                btnDiv.setBackgroundColor(Color.parseColor(COR_SELECT));
                break;
            case MULT:
                btnMult.setBackgroundColor(Color.parseColor(COR_SELECT));
                break;
            case SUB:
                btnMenos.setBackgroundColor(Color.parseColor(COR_SELECT));
                break;
            case SUM:
                btnMais.setBackgroundColor(Color.parseColor(COR_SELECT));
                break;
        }
    }

}