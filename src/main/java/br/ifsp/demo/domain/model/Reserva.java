package br.ifsp.demo.domain.model;

import br.ifsp.demo.controller.TransactionController;

import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private final String user;
    private List<Ingresso> ingressos;
    TransactionController transactionController;

    public Reserva() {
        this.user = "UID usuario";
        this.ingressos = new ArrayList<>();
    }


}
