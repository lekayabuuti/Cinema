package br.ifsp.demo.model;

import br.ifsp.demo.controller.TransactionController;
import br.ifsp.demo.security.auth.AuthenticationInfoService;
import br.ifsp.demo.security.user.User;

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
