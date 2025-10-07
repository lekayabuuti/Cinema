package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Sessao {
    private Long id;
    private final Filme filme;
    private final Horario horario;
    private final Sala sala;
    // sessão gerencia o estado dos assentos PARA ELA
    private final List<AssentoSessao> assentosDaSessao;

    public Sessao(Filme filme, Horario horario, Sala sala) {
        this.filme = filme;
        this.horario = horario;
        this.sala = sala;
        // pra cada assento físico na sala, cria um controle de status para esta sessão.
        this.assentosDaSessao = new ArrayList<>();
    }
}