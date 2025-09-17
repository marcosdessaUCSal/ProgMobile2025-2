package com.tracker.model;

import java.util.ArrayList;

public class Rastreio {

    public ArrayList<Registro> registros = new ArrayList<Registro>();
    public String codigo;

    public Rastreio(String codigo) {
        this.codigo = codigo;
    }

    public ArrayList<Registro> getRegistros() {
        return this.registros;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void addRegistro(Registro reg) {
        this.registros.add(reg);
    }



}
