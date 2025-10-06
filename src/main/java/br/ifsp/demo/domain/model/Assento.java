package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;

public class Assento {
    private final String codigo;
    private final Sala sala;

    public Assento(String fileira, int numeroAssento, Sala sala) {
        this.codigo = fileira + numeroAssento;
        this.sala = sala;
    }

    public Assento(String codigo, Sala sala) {
        this.codigo = codigo;
        this.sala = sala;
    }
}

