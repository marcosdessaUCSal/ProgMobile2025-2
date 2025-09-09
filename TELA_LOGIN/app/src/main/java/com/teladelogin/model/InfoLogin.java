package com.teladelogin.model;

import java.io.Serializable;

public class InfoLogin implements Serializable {

    public String login;
    public String senha;
    public boolean lembrar;
    public int erros;

    public InfoLogin(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.lembrar = false;
        this.erros = 0;
    }

    public void acrescentarErro() {
        this.erros++;
    }
}
