package br.ifsp.demo.domain.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Reserva {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Sessao sessao;

    @OneToOne(optional = false)
    private Ingresso ingresso;

    public Reserva() {}

    public Reserva(Sessao sessao, Ingresso ingresso) {
        this.sessao = sessao;
        this.ingresso = ingresso;
    }

    public UUID getId() {
        return id;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }
}
