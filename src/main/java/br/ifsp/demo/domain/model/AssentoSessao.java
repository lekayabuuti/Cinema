package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AssentoSessao {
    private final Assento assento;
    private final Sessao sessao;
    @Setter
    private Status status = Status.DISPONIVEL;

    public AssentoSessao(Assento assento, Sessao sessao) {
        this.assento = assento;
        this.sessao = sessao;
    }

    public boolean estaDisponivel() {
        return this.status == Status.DISPONIVEL;
    }

    /**
     * Altera o estado do assento para RESERVADO
     * Encapsula a logica de transicao de estado
     */
    public void reservar() {
        // abre espaco para adicionar regras. Exemplo: "if(status != DSIPONIVEL) throw...
        this.status = Status.RESERVADO;
    }

}