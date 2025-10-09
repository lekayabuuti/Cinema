package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.exception.AssentoIndisponivelException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Sessao {
    private Long id;
    private final Filme filme;
    private final DataHora dataHora;
    private final Sala sala;
    // sessão gerencia o estado dos assentos PARA ELA
    private final List<AssentoSessao> assentosDaSessao;

    public Sessao(Filme filme, DataHora dataHora, Sala sala) {
        this.filme = filme;
        this.dataHora = dataHora;
        this.sala = sala;
        // pra cada assento físico na sala, cria um controle de status para esta sessão.
        this.assentosDaSessao = sala.getAssentos().stream()
                .map(assento -> AssentoSessao.criarNovo(assento, this))
                .collect(Collectors.toList());
    }

    /**
     * Encontra um assento pelo código, aplica a regra de negócio de reserva
     * e altera o estado do assento.
     * @param codigoAssento O código do assento a ser reservado (ex: "A1").
     * @return O objeto AssentoSessao que foi reservado.
     */
    public AssentoSessao reservarAssento (String codigoAssento){
        //1 - Encontra o assento desejado na lista de assentos DESSA sessao
        AssentoSessao assentoParaReservar = this.assentosDaSessao.stream()
                .filter(assentoSessao -> assentoSessao.getAssento().getCodigo().equals(codigoAssento))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Assento não encontrado nessa sessão"));

        // 2- Aplica a regra de negocio (verificar se esta disponivel SOMENTE... por enquanto)
        if (!assentoParaReservar.estaDisponivel()){
            throw new AssentoIndisponivelException("Assento já está reservado");
        }

        //3 manda o objeto se reservar (encapsulamento)
        assentoParaReservar.reservar();

        return assentoParaReservar;

    }
}