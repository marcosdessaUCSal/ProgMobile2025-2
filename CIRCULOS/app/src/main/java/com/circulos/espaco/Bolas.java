package com.circulos.espaco;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class Bolas {

    private static Bolas instance = null;

    // número de bolas por cícrulo
    private static final int NUM_BOLAS = 20;

    // número de círculos
    private static final int NUM_CIRC = 10;

    private static final double RAIO_MAX = 400;

    private ArrayList<Bola> bolas;

    // Comprimento de deslocamento (acumulado) das bolas nos círculos
    private double compr;

    private double escalaX;
    private double escalaY;
    private double dTeta = 0;

    private Bolas() {
        this.bolas = new ArrayList<>();
        inicializa();
    }

    public static Bolas getInstance() {
        if (instance == null) {
            instance = new Bolas();
        }
        return instance;
    }

    public void boost(double dC) {
        compr += dC;
    }

    private void inicializa() {
        this.escalaX = 1.0F;
        this.escalaY = 1.0F;
        this.dTeta = 0;
        criaTodasAsBolas();
    }

    public void setEscalaX(float s) {
        this.escalaX = s;
    }

    public void setEscalaY(float s) {
        this.escalaY = s;
    }

    public double getEscalaX() {
        return this.escalaX;
    }

    public double getEscalaY() {
        return this.escalaY;
    }

    // Cria as bolinhas, com todas as sua propriedades (só ocorre uma vez)
    private void criaTodasAsBolas() {
        for (int i = 1; i <= NUM_CIRC; i++) {
            for (int j = 1 ; j <= NUM_BOLAS; j++) {
                Bola bola = new Bola();
                bola.coordPolar.r = i * RAIO_MAX / NUM_CIRC;
                bola.coordPolar.teta = (j - 1) * 2 * Math.PI / NUM_BOLAS;
                bola.cor = new Cor(rnd(), rnd(), rnd());
                bolas.add(bola);
            }
        }
    }

    public ArrayList<Bola> getTodasAsBolas() {
        ArrayList<Bola> novasBolas = new ArrayList<>();
        for (Bola bola : bolas) {
            Bola novaBola = new Bola(bola);
            novaBola.coordPolar.teta += compr / bola.coordPolar.r;
            novasBolas.add(novaBola);
        }
        return novasBolas;
    }

    private int rnd() {
        Random random = new Random();
        return 100 + random.nextInt(156);
    }
}
