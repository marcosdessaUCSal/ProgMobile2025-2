package com.exbtns;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textViewNumero;
    private Map<String, Integer> map;

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

        textViewNumero = findViewById(R.id.textViewNumero);
        map = new HashMap<>();
        criaMapa();
    }

    public void onClickBtn(View v) {
        int viewId = v.getId();
        String key = v.getResources().getResourceEntryName(viewId);
        int numero = map.get(key);
        textViewNumero.setText("Número: " + numero);
    }

    private void criaMapa() {
        for (int i = 1; i <= 9; i++) {
            map.put("btn" + i, i);
        }
    }
}