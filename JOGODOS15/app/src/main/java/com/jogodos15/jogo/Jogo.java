package com.jogodos15.jogo;

import com.jogodos15.model.ElmMatriz;
import com.jogodos15.model.RespostaMovimento;

import java.util.ArrayList;

public class Jogo {

    private static Jogo instance = null;

    private final int[][] matriz = new int[4][];
    private final int[][] matrizMeta = new int[4][];

    private Jogo() {
        iniciaMatriz();
        iniciaMatrizMeta();
    }

    public static Jogo getInstance() {
        if (instance == null) {
            instance = new Jogo();
        }
        return instance;
    }

    private void iniciaMatriz() {
        this.matriz[0] = new int[]{1, 2, 3, 4};
        this.matriz[1] = new int[]{5, 6, 7, 8};
        this.matriz[2] = new int[]{9, 10, 11, 12};
        this.matriz[3] = new int[]{13, 14, 15, 0};
    }

    private void iniciaMatrizMeta() {
        this.matrizMeta[0] = new int[]{1, 2, 3, 4};
        this.matrizMeta[1] = new int[]{5, 6, 7, 8};
        this.matrizMeta[2] = new int[]{9, 10, 11, 12};
        this.matrizMeta[3] = new int[]{13, 14, 15, 0};
    }

    // verificar se terminou o jogo
    public boolean gameOver() {
        boolean resultado = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.matriz[i][j] != this.matrizMeta[i][j]) {
                    resultado = false;
                }
            }
        }
        return resultado;
    }

    // Recebe informação de um clique e dá uma resposta (classe RespostaMovimento)
    // Se o movimento foi possível, pode-se chamar o metodo efetivaMovimento
    public RespostaMovimento clicarPeca(int lin, int col) {
        RespostaMovimento resM = new RespostaMovimento();
        resM.origem = new ElmMatriz(lin, col);
        ElmMatriz vazio = procuraVazio();
        resM.destino = new ElmMatriz(vazio.linha, vazio.coluna);
        resM.numPec = this.matriz[lin][col];
        resM.possivel = false;
        int linV = vazio.linha;
        int colV = vazio.coluna;
        int dL = Math.abs(lin - linV);
        int dC = Math.abs(col - colV);
        boolean cond1 = dL == 1 && dC == 0;
        boolean cond2 = dL == 0 && dC == 1;
        if (cond1 || cond2) {
            resM.possivel = true;
        }
        return resM;
    }

    public void efetivaMovimento(RespostaMovimento resM) {
        this.matriz[resM.destino.linha][resM.destino.coluna] = resM.numPec;
        this.matriz[resM.origem.linha][resM.origem.coluna] = 0;
    }

    private ElmMatriz procuraVazio() {
        ElmMatriz resp = null;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.matriz[i][j] == 0) {
                    resp = new ElmMatriz(i, j);
                }
            }
        }
        return resp;
    }

    public ArrayList<ElmMatriz> getPecasMoveis() {
        ArrayList<ElmMatriz> pecMov = new ArrayList<>();
        ElmMatriz vazio = procuraVazio();
        int linV = vazio.linha;
        int colV = vazio.coluna;
        // topo
        if (linV > 0) {
            pecMov.add(new ElmMatriz(linV - 1, colV));
        }
        // base
        if (linV < 3) {
            pecMov.add(new ElmMatriz(linV + 1, colV));
        }
        // esquerda
        if (colV > 0) {
            pecMov.add(new ElmMatriz(linV, colV - 1));
        }
        // direita
        if (colV < 3) {
            pecMov.add(new ElmMatriz(linV, colV + 1));
        }
        return pecMov; // necessariamente, tem 2, 3 ou 4 elementos
    }

    public int[][] getMatriz() {
        return this.matriz;
    }

    public boolean isMovel(int lin, int col) {
        boolean resposta = false;
        ArrayList<ElmMatriz> pecMov = getPecasMoveis();
        for (ElmMatriz p : pecMov) {
            if (p.linha == lin && p.coluna == col) {
                resposta = true;
                break;
            }
        }
        return resposta;
    }

    // embaralha as peças (faz movimentos válidos aleatoriamente)
    public void embaralha(int nivel) {
        ArrayList<ElmMatriz> opcoes;
        int indiceEscolha;
        int escLin;
        int escCol;
        for (int i = 1; i <= nivel; i++) {
            opcoes = getPecasMoveis();
            indiceEscolha = (int) Math.floor(Math.random() * opcoes.size());
            escLin = opcoes.get(indiceEscolha).linha;
            escCol = opcoes.get(indiceEscolha).coluna;
            RespostaMovimento resM = clicarPeca(escLin, escCol);
            efetivaMovimento(resM);
        }
    }

    public void resetMatriz() {
        this.iniciaMatriz();
    }

}
