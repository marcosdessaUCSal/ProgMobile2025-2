package com.miniplayer.audio;

import android.media.MediaPlayer;

public class MusicTrack {

    public String key;
    public int resId;   // id do recurso no app (na pasta raw)
    public String name;
    public int duration;    // em milissegundos
    public MediaPlayer mediaPlayer;

    public MusicTrack() {}

    public MusicTrack(String key, int id, String nome, MediaPlayer mediaPlayer) {
        this.key = key;
        this.resId = id;
        this.name = nome;
        this.mediaPlayer = mediaPlayer;
        this.duration = mediaPlayer.getDuration();
    }

    public MusicTrack(String key, int id, MediaPlayer mediaPlayer) {
        this.key = key;
        this.resId = id;
        this.mediaPlayer = mediaPlayer;
        this.name = "";     // para evitar null
        this.duration = mediaPlayer.getDuration();
    }


}
