package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Sessao {
    private Long id;
    private final Filme filme;
    private final Horario horario;
    private final Sala sala;
    private final List<Ingresso> ingressos;

    public Sessao(Filme filme, Horario horario, Sala sala, List<Ingresso> ingressos) {
        this.filme = filme;
        this.horario = horario;
        this.sala = sala;
        this.ingressos = ingressos;
    }

    public void reservarAssento(String codigoAssento, Reserva reserva){
        Ingresso ingressoParaReservar = this.ingressos.stream()
                .filter(ingresso -> ingresso.getAssento().getCodigo().equals(codigoAssento))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Assento " + codigoAssento + "não encontrado nesta sessão"));
        if (ingressoParaReservar.getStatus() != Status.DISPONIVEL){
            throw new IllegalStateException("O assento " + codigoAssento + "não está disponível");
        }

        ingressoParaReservar.setStatus(Status.RESERVADO);
        ingressoParaReservar.setReserva(reserva);
        reserva.getIngressos().add(ingressoParaReservar);

    }

    public List<Ingresso> getIngressos() {
        return List.copyOf(ingressos);
    }
}

