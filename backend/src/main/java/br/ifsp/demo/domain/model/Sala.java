package br.ifsp.demo.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Sala {
    private final Integer numeroSala;
    private List<Assento> assentos;

    public Sala(Integer numeroSala) {
        this.numeroSala = numeroSala;
        this.assentos = new ArrayList<>();
    }

}
