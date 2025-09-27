package br.ifsp.demo.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private final Integer numeroSala;
    private List<Assento> assentos;

    public Sala(Integer numeroSala) {
        this.numeroSala = numeroSala;
        this.assentos = new ArrayList<>();
    }

    public Integer getNumeroSala() {
        return numeroSala;
    }
}
