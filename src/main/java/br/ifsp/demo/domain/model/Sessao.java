package br.ifsp.demo.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class Sessao {
    private Long id;
    private final Filme filme;
    private final Horario horario;
    private final Sala sala;
    private final List<Ingresso> ingressosReservados;

    public Sessao(Filme filme, Horario horario, Sala sala) {
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

