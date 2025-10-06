package br.ifsp.demo.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sessao {
    private final String id;
    private final Filme filme;
    private final Horario horario;
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

    public String getId() { return id; }
    public Filme getFilme() { return filme; }
    public Horario getHorario() { return horario; }
    public Sala getSala() { return sala; }
}

