package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AssentoSessao {
    private final Assento assento;
    private final Sessao sessao;
    private Status status;

    private AssentoSessao(Assento assento, Sessao sessao) {
        this.assento = assento;
        this.sessao = sessao;
    }

    public static AssentoSessao criarNovo(Assento assento, Sessao sessao){
        AssentoSessao novoAssentoSessao = new AssentoSessao(assento, sessao);
        novoAssentoSessao.status = Status.DISPONIVEL;
        return novoAssentoSessao;
    }

    /**
     * Para RECONSTRUIR um AssentoSessao a partir do banco de dados
     * Usado pela camada de persistencia (mapper).
     */
    public static AssentoSessao reconstituir(Assento assento, Sessao sessao, Status status){
        AssentoSessao assentoSessao = new AssentoSessao(assento, sessao);
        assentoSessao.status = status;
        return assentoSessao;
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