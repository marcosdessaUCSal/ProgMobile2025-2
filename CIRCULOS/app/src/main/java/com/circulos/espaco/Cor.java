package com.circulos.espaco;

public class Cor {

    public int r;
    public int g;
    public int b;

    public Cor() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public Cor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String toString() {
        return String.format("#%02X%02X%02X", r, g, b);
    }
}
