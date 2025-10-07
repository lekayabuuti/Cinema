package br.ifsp.demo.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Reserva {

    private Long id;

    // Uma reserva possuirá uma Lista de ingressos
    private List<Ingresso> ingressos = new ArrayList<>();

    private Sessao sessao;
}
