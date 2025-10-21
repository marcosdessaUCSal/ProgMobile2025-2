package com.circulos.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.circulos.espaco.Bola;
import com.circulos.espaco.Bolas;

public class BolasCanvas extends View {

    // localização do centro
    private float xC;
    private float yC;

    // dimensões do canvas
    private int cvWidth;
    private int cvHeight;

    private Paint paint;

    private Bolas bolas;

    public BolasCanvas(Context context) {
        super(context);
    }

    public BolasCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        bolas = Bolas.getInstance();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        this.xC = (float) cvWidth / 2;
        this.yC = (float) cvHeight / 2;
        desenhaBolinhas(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.cvWidth = w;
        this.cvHeight = h;
        invalidate();
    }

    public void atualizaImagem() {
        invalidate();
    }

    private void desenhaBolinhas(Canvas canvas) {
        float x;
        float y;
        float r;
        float teta;
        float h;
        float sx = (float) bolas.getEscalaX();
        float sy = (float) bolas.getEscalaY();
        for (Bola bola : bolas.getTodasAsBolas()) {
            r = (float) bola.coordPolar.r;
            teta = (float) bola.coordPolar.teta;
            x = (float) (xC + sx * r * Math.cos(teta));
            y = (float) (yC - sy * r * Math.sin(teta));
            paint.setColor(Color.parseColor(bola.cor.toString()));
            canvas.drawCircle(x, y, (float) bola.tamanho, paint);
        }
    }
}
