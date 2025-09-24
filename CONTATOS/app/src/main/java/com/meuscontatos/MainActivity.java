package com.meuscontatos;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meuscontatos.adapters.ContatosAdapter;
import com.meuscontatos.dao.Dao;

public class MainActivity extends AppCompatActivity {

    private ContatosAdapter contatosAdapter;
    private RecyclerView recyclerView;

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

        // Introduzindo o adapter
        contatosAdapter = new ContatosAdapter();

        // Vinculando-se ao RecyclerView do layout da activity
        recyclerView = findViewById(R.id.recyclerview);

        // Conectando o adaptador ao RecyclerView, onde os contatos devem aparecer
        recyclerView.setAdapter(contatosAdapter);

        // Define um tipo de layout a ser utilizado no recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contatosAdapter.setListener(new ContatosAdapter.Listener() {
            @Override
            public void onClick(int position) {

                // Remove o contato clicado
                Dao.getInstance().getContatos().remove(position);

                // Notificando o adapter de que houve remoção
                // OBS: se você quiser adicionar algo, use notifyItemInserted(position)
                contatosAdapter.notifyItemRemoved(position);

                // Para notificar uma alteração
                contatosAdapter.notifyItemChanged(position, Dao.getInstance().getContatos().size());
            }
        });

    }


    public void reset(View view) {
        Dao.getInstance().reset();
        contatosAdapter.notifyDataSetChanged();
    }
}