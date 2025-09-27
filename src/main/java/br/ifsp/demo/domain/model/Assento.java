package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.model.enumerations.Status;

public class Assento {
    private final String codigo;
    private final Sala sala;
    private Status status;

    public Assento(String codigo, Sala sala) {
        this.codigo = codigo;
        this.sala = sala;
        this.status = Status.DISPONIVEL;
    }
}

