package com.circulos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.circulos.canvas.BolasCanvas;
import com.circulos.espaco.Bolas;

public class MainActivity extends AppCompatActivity {

    private boolean girando = false;

    private BolasCanvas canvas;
    private SeekBar sbHorizontal;
    private SeekBar sbVertical;
    private SeekBar sbRot;
    private Handler handler;
    private Bolas bolas;

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

        bolas = Bolas.getInstance();

        handler = new Handler(Looper.getMainLooper());

        sbHorizontal = findViewById(R.id.sb_horizontal);
        sbVertical = findViewById(R.id.sb_vertical);
        sbRot = findViewById(R.id.sb_rot);

        canvas = findViewById(R.id.canvas);


    }

    private void iniciaTemporizador() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                bolas.boost(Float.valueOf(sbRot.getProgress()) / 50);
                bolas.setEscalaX(Float.valueOf(sbHorizontal.getProgress()) / 50);
                bolas.setEscalaY(Float.valueOf(sbVertical.getProgress()) / 50);
                canvas.atualizaImagem();
                if (girando) {
                    handler.postDelayed(this, 15);
                }
            }
        });
    }

    public void onClickIniciar(View view) {
        girando = true;
        iniciaTemporizador();
    }

    public void onClickParar(View view) {
        girando = false;
    }
}