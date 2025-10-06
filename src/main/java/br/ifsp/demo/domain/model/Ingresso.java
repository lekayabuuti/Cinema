package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingresso {
    private Assento assento;
    private Status status;
    private Reserva reserva;

    public Ingresso(Assento assento) {
        this.assento = assento;
        this.status = Status.DISPONIVEL;
    }
}
