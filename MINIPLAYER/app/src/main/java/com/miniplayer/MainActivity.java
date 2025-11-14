package com.miniplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.miniplayer.audio.MusicManager;
import com.miniplayer.audio.MusicTrack;
import com.miniplayer.user_interface.Musica;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // PARÂMETROS
    private int volumeEfeitos;
    private int musicaEmFoco;    // 0, 1, 2 (Ex de 1, 2, 3)
    private boolean tocando = false;

    // CONSTANTES
    private MusicManager music;
    private SeekBar sbVolMusicas;
    private SeekBar sbProgressoMusica;
    private TextView tvVisorPlayer;
    private TextView tvVisorMusica;
    private ArrayList<Musica> listaMusicas;

    // TEMPORIZADORES
    private Handler handler;



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

        // GERENCIADOR DE MÚSICAS
        music = MusicManager.getInstance(this);
        listaMusicas = new ArrayList<>();

        // TEXT VIEWS
        tvVisorPlayer = findViewById(R.id.tv_visor_player);
        tvVisorMusica = findViewById(R.id.tv_visor_musica);

        // TEMPORIZADORES
        handler = new Handler(Looper.getMainLooper());

        // SEEKBARS
        sbVolMusicas = findViewById(R.id.sb_vol_musica);
        sbProgressoMusica = findViewById(R.id.sb_progresso_musica);
        // insere listener para o progresso da música
        sbProgressoMusica.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            // Quando o usuário toca na barra de progresso
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!tocando) {
                    sbProgressoMusica.setProgress(0);
                    return;
                }
                float progrMusicaEmFoco = sbProgressoMusica.getProgress();
                float duracao = music.getDuration();
                float posAtual = 0.01f * progrMusicaEmFoco * duracao;
                music.seekTo((int) posAtual);

            }
        });

        // AÇÕES INICIAIS
        criandoListaComMusicas();
        iniciaTemporizador();
    }

    // TEMPORIZADOR
    private void iniciaTemporizador() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // visores do player de mídia
                if (tocando) atualizaVisoresMidia();

                // volume da música
                float vol = (float) sbVolMusicas.getProgress()/100.0f;
                music.setVolume(vol);

                handler.postDelayed(this, 30);
            }
        });
    }

    // Atualiza visores do player de mídia
    private void atualizaVisoresMidia() {
        float comprProgrBar = sbProgressoMusica.getProgress();

        // cálculo do comprimento da progress bar
        if (listaMusicas.get(musicaEmFoco).key.equals(music.getCurrentKey())) {
            int posAtual = music.getCurrentPosition();
            float percentual = 100 * posAtual / music.getDuration();
            sbProgressoMusica.setProgress((int) Math.floor(percentual));

            // atualizando a estrutura de dados
            listaMusicas.get(musicaEmFoco).posAtual = posAtual;

            // contador do tempo
            String visor = music.getFormattedCurrentPosition();
            tvVisorPlayer.setText(visor);
        }


    }


    // ========================================
    //      MÉTODOS ONCLICK
    // ========================================

    // BOTÃO PLAY DO PAINEL DE MÍDIA
    public void onClickPlay(View view) {
        if (!tocando) return;
        listaMusicas.get(musicaEmFoco).pausada = false;
        music.play(listaMusicas.get(musicaEmFoco).key);
    }

    // BOTÃO PAUSE DO PAINEL DE MÍDIA
    public void onClickPause(View view) {
        if (!tocando) return;
        listaMusicas.get(musicaEmFoco).pausada = true;
        music.pause();
    }

    // BOTÕES DE AMOSTRAS DE MÚSICAS
    public void onClickEx(View view) {
        // identificando o botão específico
        int viewId = view.getId();
        String resId = getResources().getResourceEntryName(viewId);
        int ex; // (Ex 1, Ex2, Ex3) = (0, 1, 2)
        switch (resId) {
            case "btn_ex1":
                ex = 0;
                break;
            case "btn_ex2":
                ex = 1;
                break;
            default:
                ex = 2;
        }

        tocando = true;
        musicaEmFoco = ex;
        if (!listaMusicas.get(ex).pausada) {
            music.play(listaMusicas.get(ex).key);
        } else {
            music.pause();
            float percentual = 100 * listaMusicas.get(ex).posAtual / music.getDuration();
            sbProgressoMusica.setProgress((int) Math.floor(percentual));
            String visor = music.getFormattedAnyPosition(listaMusicas.get(ex).posAtual);
            tvVisorPlayer.setText(visor);
        }
        tvVisorMusica.setText(listaMusicas.get(ex).nome);
    }


    // ===============================================
    //  SOBRE ESTRUTURAS DE DADOS ENVOLVENDO MÍDIAS
    // ===============================================

    // CONSTRUINDO LISTA COM AS MÚSICAS
    private void criandoListaComMusicas() {
        Map<String, MusicTrack> map = music.getAllTracksMap();
        listaMusicas.add(new Musica(map.get("ANOTHER_DAY_IN_PARADISE")));
        listaMusicas.add(new Musica(map.get("BIG_IN_JAPAN")));
        listaMusicas.add(new Musica(map.get("EVERY_BREATH_YOU_TAKE")));

    }
}