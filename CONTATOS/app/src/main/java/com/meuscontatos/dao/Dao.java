package com.meuscontatos.dao;

import com.meuscontatos.model.Contato;

import java.util.ArrayList;

public class Dao {

    private ArrayList<Contato> contatos;

    private static Dao instance = null;

    private Dao() {
        contatos = new ArrayList<>();
        geraLista();
    }

    public static Dao getInstance() {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }

    public ArrayList<Contato> getContatos() {
        return this.contatos;
    }

    public void reset() {
        this.contatos.clear();
        geraLista();
    }

    private void geraLista() {
        // contato 1
        this.contatos.add(new Contato("Fulano de Tal", "fulanodetal@email.com",
                "(71) 99999 1234"));
        // contato 2
        this.contatos.add(new Contato("Sicrano", "siucrano@email.com",
                "(71) 98888 4321"));
        // contato 3
        this.contatos.add(new Contato("Beltrano Jr", "beljr@email.com",
                "(71) 98786 1645"));
        // contato 4
        this.contatos.add(new Contato("Senhor Dr Tiririca", "srtiririca@email.com",
                "(71) 99123 1345"));
        // contato 5
        this.contatos.add(new Contato("Excelentíssimo Cidadão", "cidadao@email.com",
                "(71) 90012 1287"));
        // contato 6
        this.contatos.add(new Contato("Um Amigo", "amigo@email.com",
                "(71) 9987 9978"));


    }
}
