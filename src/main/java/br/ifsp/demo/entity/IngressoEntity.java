package br.ifsp.demo.entity;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.Assento;
import lombok.Data;

@Data // cria getters, setters, toString, etc. automaticamente
public class IngressoEntity {
    private Long id;
    private Assento assento;
    private Status status;
    private SessaoEntity sessao;
}