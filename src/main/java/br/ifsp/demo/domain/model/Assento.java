package br.ifsp.demo.domain.model;

public class Assento {
    private final String codigo;
    private final Sala sala;

    public Assento(String codigo, Sala sala) {
        this.codigo = codigo;
        this.sala = sala;
    }

    public String getCodigo() {
        return codigo;
    }

    public Sala getSala() {
        return sala;
    }

}

