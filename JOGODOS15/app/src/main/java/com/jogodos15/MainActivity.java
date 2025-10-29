package com.jogodos15;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jogodos15.jogo.Jogo;
import com.jogodos15.model.Botao;
import com.jogodos15.model.RespostaMovimento;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final double PERC_LARGURA = 0.90;
    private final double PERC_ALTURA = 0.90;
    private final String COR_PECA = "002699";
    private final String COR_MOVEL = "408699";
    private final String COR_PECA_BLOQ = "000000";
    private final String COR_MOVEL_BLOQ = "000000";
    private final int NIVEL = 200;

    private LayoutInflater inflater;
    private TableLayout tablayTabuleiro;
    private CardView cvGameOver;

    private final HashMap<Integer, Botao> tabuleiro = new HashMap<>();
    private final Jogo jogo = Jogo.getInstance();

    // a respeito da jogada
    private boolean iniciado = false;
    private boolean gameOver = false;

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

        // cardview do GAME OVER (inicialmente ausente)
        cvGameOver = findViewById(R.id.cv_gameover);
        cvGameOver.setVisibility(View.GONE);

        // definindo as proporções do painel
        int largura = (int) Math.floor(obtemLarguraEmPixels() * PERC_LARGURA);
        int altura = (int) Math.floor(obtemLarguraEmPixels() * PERC_ALTURA);
        tablayTabuleiro = findViewById(R.id.tablay_tabuleiro);
        tablayTabuleiro.getLayoutParams().width = largura;
        tablayTabuleiro.getLayoutParams().height = altura;
        tablayTabuleiro.requestLayout();

        // construção dos botões na activity -> dados guardados em 'tabuleiro' (sem formatação)
        inflater = LayoutInflater.from(this);
        tablayTabuleiro = findViewById(R.id.tablay_tabuleiro);
        int btnId = 0;
        for (int i = 0; i < 4; i++) {
            TableRow tr = new TableRow(this);

            // ajustando as margens dos botões
            TableRow.LayoutParams paramsRow = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            paramsRow.setMargins(0, 0, 0, 0);

            tr.setMinimumHeight(altura / 4);
            for (int j = 0; j < 4; j++) {
                Button btn = (Button) inflater.inflate(R.layout.btn_template, tr, false);
                btn.setId(btnId);
                Botao botao = new Botao();
                botao.btn = btn;
                botao.linha = i;
                botao.coluna = j;
                tabuleiro.put(btnId, botao);
                tr.addView(btn);
                btnId++;
                // definindo o listener para cliques
                botao.btn.setOnClickListener(view -> clicar(view));
            }
            tablayTabuleiro.addView(tr);
        }

        refreshTabuleiro();
    }

    // Obtém a largura da tela em pixels
    private int obtemLarguraEmPixels() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    // Contrói o tabuleiro com base em informações da classe Jogo (sem animações)
    private void refreshTabuleiro() {

        sincronizaBotoes();
        Botao botao;
        String cor;
        String corMovel = iniciado? COR_MOVEL : COR_MOVEL_BLOQ;
        String corPeca = iniciado? COR_PECA : COR_PECA_BLOQ;
        for (Map.Entry<Integer, Botao> obj : tabuleiro.entrySet()) {
            botao = obj.getValue();
            if (botao.numPeca != 0) {
                cor = botao.movel ? "#" + corMovel : "#" + corPeca;
                botao.btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(cor)));
                botao.btn.setText(Integer.toString(botao.numPeca));
                botao.btn.setElevation(8f);
                botao.btn.setTranslationZ(8f);
            } else {
                botao.btn.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                botao.btn.setText("");
                botao.btn.setElevation(0);
                botao.btn.setTranslationZ(-1);

            }
            botao.btn.invalidate();
            botao.btn.requestLayout();
        }
    }

    // sincroniza a classe Botao
    private void sincronizaBotoes() {
        int[][] matriz = jogo.getMatriz();
        Botao botao;
        for (Map.Entry<Integer, Botao> obj : tabuleiro.entrySet()) {
            botao = obj.getValue();
            botao.numPeca = matriz[botao.linha][botao.coluna];
            botao.movel = jogo.isMovel(botao.linha, botao.coluna);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            refreshTabuleiro();
        }

    }




    /*
        ========== RECURSOS DE INTERAÇÃO COM O USUÁRIO ================
     */

    // processa o clique sobre uma peça
    private void clicar(View view) {
        if (!iniciado || gameOver) return;

        int id = view.getId();
        int linha = tabuleiro.get(id).linha;
        int coluna = tabuleiro.get(id).coluna;
        RespostaMovimento resM = jogo.clicarPeca(linha, coluna);
        if (resM.possivel) {
            jogo.efetivaMovimento(resM);
            refreshTabuleiro();
        }
        if (jogo.gameOver()) {
            defineGameOver();
        }
    }

    // BOTÃO PLAY
    public void clickPlay(View view) {
        if (!iniciado && !gameOver) {
            iniciado = true;
            jogo.embaralha(NIVEL);
            refreshTabuleiro();
        }
    }

    // BOTÃO RESET
    public void clickReset(View view) {
        if (iniciado && !gameOver) {
            iniciado = false;
            jogo.resetMatriz();
            refreshTabuleiro();
        }
    }

    // GAME OVER : VITÓRIA
    private void defineGameOver() {
        cvGameOver.setVisibility(View.VISIBLE);
        gameOver = true;
    }

    // OK para VOCÊ VENCEU
    public void clickOkVoceVenceu(View view) {
        cvGameOver.setVisibility(View.GONE);
        gameOver = false;
        iniciado = false;
        refreshTabuleiro();
    }
}