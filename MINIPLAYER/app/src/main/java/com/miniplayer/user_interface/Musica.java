package com.miniplayer.user_interface;

import com.miniplayer.audio.MusicTrack;

public class Musica {

    public String key;
    public String nome = "";
    public int duracao;
    public int posAtual = 0;    // milissegundos
    public boolean pausada = false;

    public Musica() {}

    public Musica(MusicTrack track) {
        this.key = track.key;
        this.nome = track.name;
        this.duracao = track.duration;
    }

    public Musica(String key, String nome, int duracao) {
        this.key = key;
        this.nome = nome;
        this.duracao = duracao;
    }
}