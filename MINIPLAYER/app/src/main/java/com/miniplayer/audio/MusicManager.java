package com.miniplayer.audio;

import android.content.Context;
import android.media.MediaPlayer;

import com.miniplayer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicManager {
    private static MusicManager instance = null;

    private Context ctx;
    private final Map<String, MusicTrack> trackMap = new HashMap<>();
    private MusicTrack currentTrack = null;

    private MusicManager(Context ctx) {
        this.ctx = ctx.getApplicationContext();
        initializeTracks();
    }

    public static MusicManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new MusicManager(ctx);
        }
        return instance;
    }

    // INICIALIZANDO TODAS AS MÚSICAS DO APP
    private void initializeTracks() {
        addMusic("ANOTHER_DAY_IN_PARADISE", R.raw.music_another_day_in_paradise, "Another Day In Paradise");
        addMusic("BIG_IN_JAPAN", R.raw.music_big_in_japan, "Big In Japan");
        addMusic("EVERY_BREATH_YOU_TAKE", R.raw.music_every_breath_you_take, "Every Breath You Take");
    }

    // ADICIONA UMA MÚSICA
    private void addMusic(String key, int resId, String name) {
        MediaPlayer player = MediaPlayer.create(ctx, resId);
        if (player != null) {
            player.setLooping(false);   // alterar isso se looping for necessário
            MusicTrack track = new MusicTrack(key, resId, name, player);
            trackMap.put(key, track);
        }
    }



    // =======================================
    //      CONTROLE DA EXECUÇÃO
    // =======================================

    public void play(String key) {
        // Se em execução, a trilha atual será parada
        if (currentTrack != null) {
            // solicitar 'play' para a mesma música enquanto está sendo tocada: irrelevante
            if (key.equals(currentTrack.key) && currentTrack.mediaPlayer.isPlaying()) {
                return;
            }
            pause();
            stop();
        }
        // Inicia a execução solicitada
        MusicTrack track = trackMap.get(key);
        if (track != null) {
            currentTrack = track;
            currentTrack.mediaPlayer.start();
        }
    }

    // Define a música de foco sem executá-la
    public void putCurrentTrack(String key) {
        MusicTrack track = trackMap.get(key);
        currentTrack = track;
    }

    public void pause() {
        if (currentTrack != null && currentTrack.mediaPlayer.isPlaying()) {
            currentTrack.mediaPlayer.pause();
        }
    }


    public void stop() {
        if (currentTrack != null) {
            MediaPlayer player = currentTrack.mediaPlayer;
            if (player.isPlaying()) {
                player.stop();
            }
            try {
                player.prepare();
            } catch (Exception e) {
                //TODO: DEFINIR O QUE FAZER
            }
        }
    }

    // para o caso (se necessário) de se encerrar completamente o player
    public void release() {
        for (MusicTrack track : trackMap.values()) {
            try {
                track.mediaPlayer.release();
            } catch (Exception ignored) {}
        }
        trackMap.clear();
        currentTrack = null;
        instance = null;
    }

    // =======================================
    //      MÉTODOS AUXILIARES
    // =======================================

    // O uso só tem efeito para música em execução
    public void setVolume(float volume) {
        if (currentTrack != null) {
            currentTrack.mediaPlayer.setVolume(volume, volume);
        }
    }

    public void setLoop(String key, boolean loop) {
        MusicTrack track = trackMap.get(key);
        if (track != null) {
            track.mediaPlayer.setLooping(loop);
        }
    }

    public void seekTo(int millis) {
        if (currentTrack != null) {
            currentTrack.mediaPlayer.seekTo(millis);
        }
    }

    public int getCurrentPosition() {
        if (currentTrack != null) {
            return currentTrack.mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public String getCurrentTrackKey() {
        if (currentTrack != null) {
            return currentTrack.key;
        }
        return "";
    }

    public String getFormattedCurrentPosition() {
        int millis = getCurrentPosition();
        int minutes = millis / 60000;
        int seconds = (millis % 60000) / 1000;
        int milliseconds = (millis % 1000) / 100;
        return String.format("%02d:%02d.%01d", minutes, seconds, milliseconds);
    }

    public String getFormattedAnyPosition(int millis) {
        int minutes = millis / 60000;
        int seconds = (millis % 60000) / 1000;
        int milliseconds = (millis % 1000) / 100;
        return String.format("%02d:%02d.%01d", minutes, seconds, milliseconds);
    }

    public int getDuration() {
        if (currentTrack != null) {
            return currentTrack.duration;
        }
        return 0;
    }

    public boolean isPlaying() {
        return currentTrack != null && currentTrack.mediaPlayer.isPlaying();
    }

    public String getCurrentKey() {
        return currentTrack != null ? currentTrack.key : null;
    }

    public List<MusicTrack> getAllTracksList() {
        return new ArrayList<>(trackMap.values());
    }

    public Map<String, MusicTrack> getAllTracksMap() {
        return trackMap;
    }

}
