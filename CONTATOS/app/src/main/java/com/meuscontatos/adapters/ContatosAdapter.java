package com.meuscontatos.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meuscontatos.R;
import com.meuscontatos.dao.Dao;
import com.meuscontatos.model.Contato;

import java.util.ArrayList;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ContatosViewHolder> {

    // Aqui estão os dados que o adaptador deve receber (no array list 'contatos')
    private ArrayList<Contato> contatos;
    private Listener listener;

    // CONSTRUTOR: precisa receber a estrutura de dados (com os contatos)
    // Pode receber externamente ou internamente (via DB, por exemplo)
    public ContatosAdapter() {
        this.contatos = Dao.getInstance().getContatos();
    }

    // Esta interface deve ser implementada no contexto que usa este adapter
    public interface Listener{
        void onClick(int position);
    }

    // O listener implementado externamente é introduzino aqui neste adaptador
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    // IMPORTANTE: use o ViewHolder desta classe aninhada (ContatosViewHolder)
    // Isto cria um view holder para cada cardview que será exibido
    // O view holder é criado a partir de ContatosViewHolder
    @NonNull
    @Override
    public ContatosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Criando uma referência ao card view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cv = (CardView) inflater.inflate(R.layout.card_contato, parent, false);
        return new ContatosViewHolder(cv);
    }

    // Insere dados no card view (card que contém o contato)
    @Override
    public void onBindViewHolder(@NonNull ContatosViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView tvNome = cardView.findViewById(R.id.tv_nome);
        TextView tvEmail = cardView.findViewById(R.id.tv_email);
        TextView tvTel = cardView.findViewById(R.id.tv_tel);

        // Inserindo os dados do contato no card
        tvNome.setText(contatos.get(position).nome);
        tvEmail.setText(contatos.get(position).email);
        tvTel.setText(contatos.get(position).tel);

        // Estabelece o listener para cliques do mouse
        cardView.setOnClickListener(
                v -> {
                    int posAtual = holder.getAdapterPosition();
                    if (posAtual != RecyclerView.NO_POSITION) {
                        listener.onClick(posAtual);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public static class ContatosViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ContatosViewHolder(@NonNull CardView view) {
            super(view);
            cardView = view;
        }
    }
}
