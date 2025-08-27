package com.botoes.model;

import android.widget.Button;

import java.util.HashMap;

public class Botoes {

    public static final int NUM_LINHAS = 10;
    public static final int NUM_COLUNAS = 10;
    private final boolean[] arrayQuads = new boolean[NUM_COLUNAS * NUM_LINHAS];

    public Botoes() {
        // inicializa os botões
        reset();
    }

    public void reset() {
        for (int id = 0; id < arrayQuads.length; id++) {
            arrayQuads[id] = false;
        }
    }

    // indica se um botão está aceso
    public boolean aceso(int id) {
        if (id < 0 || id >= NUM_COLUNAS * NUM_LINHAS) return false;
        return arrayQuads[id];
    }

    // clica em um botão
    public void click(int id) {
        if (id < 0 || id >= NUM_COLUNAS * NUM_LINHAS) return;
        arrayQuads[id] = !arrayQuads[id];
    }

    // inverte todos os botões
    public void inverter() {
        for (int id = 0; id < NUM_LINHAS * NUM_COLUNAS; id++) {
            click(id);
        }
    }

}
