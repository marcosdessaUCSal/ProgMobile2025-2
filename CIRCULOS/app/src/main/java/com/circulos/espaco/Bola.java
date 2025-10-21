package com.circulos.espaco;

public class Bola {

    public CoordPolar coordPolar;
    public Cor cor;
    public double tamanho;

    public Bola() {
        this.coordPolar = new CoordPolar();
        this.cor = new Cor();
        this.tamanho = 3; // default

    }

    // Construtor de cópia
    public Bola(Bola b) {
        this.coordPolar = new CoordPolar(b.coordPolar.r, b.coordPolar.teta);
        this.cor = new Cor(b.cor.r, b.cor.g, b.cor.b);
        this.tamanho = b.tamanho;
    }


}
