package br.ifsp.demo.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sessao {
    @Getter
    private final String id;
    @Getter
    private final Filme filme;
    @Getter
    private final Horario horario;
    @Getter
    private final Sala sala;
    private final List<Ingresso> ingressosReservados;

    public Sessao(Filme filme, Horario horario, Sala sala) {
        this.id = UUID.randomUUID().toString();
        this.filme = filme;
        this.horario = horario;
        this.sala = sala;
        this.ingressosReservados = new ArrayList<>();
    }

    public void adicionarIngresso(Ingresso ingresso) {
        ingressosReservados.add(ingresso);
    }

    public List<Ingresso> getIngressosReservados() {
        return List.copyOf(ingressosReservados);
    }

}

