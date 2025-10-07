package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;
import lombok.Getter;

@Getter
public class Ingresso {
    private final AssentoSessao assentoSessao; // Contém todas as informações (assento, sessão, status)


    // o ingresso SÓ é criado a partir de uma reserva de assento bem sucedida
    public Ingresso(AssentoSessao assentoSessao) {
        if (assentoSessao.getStatus() != Status.RESERVADO) {
            throw new IllegalArgumentException("Não é possível gerar um ingresso para um assento que não está reservado.");
        }
        this.assentoSessao = assentoSessao;
    }

    // metodos de conveniencia para "imprimir" o ingresso
    public String getCodigoAssento() {
        return assentoSessao.getAssento().getCodigo();
    }

    public Filme getFilme() {
        return assentoSessao.getSessao().getFilme();
    }

    public Horario getHorario() {
        return assentoSessao.getSessao().getHorario();
    }
}