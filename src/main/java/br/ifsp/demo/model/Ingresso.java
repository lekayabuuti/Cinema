package br.ifsp.demo.model;

public class Ingresso {
    private Assento assento;
    private Reserva reserva;

    public Ingresso(Assento assento, Reserva reserva) {
        this.assento = assento;
        this.reserva = reserva;
    }

    public Assento getAssento() {
        return assento;
    }

    public Reserva getReserva() {
        return reserva;
    }
}
