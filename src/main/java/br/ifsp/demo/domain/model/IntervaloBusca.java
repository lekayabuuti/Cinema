package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.exception.DataInvalidaException;
import br.ifsp.demo.domain.exception.DataPassadaException;
import java.time.LocalDate;
import java.util.Objects;

public record IntervaloBusca(LocalDate dataInicial, LocalDate dataFinal) {

    private static final int LIMITE_DIAS = 7;

    public IntervaloBusca {

        Objects.requireNonNull(dataInicial, "Data inicial não pode ser nula.");
        Objects.requireNonNull(dataFinal, "Data final não pode ser nula.");


        if (dataInicial.isAfter(dataFinal)) {
            throw new DataInvalidaException("Data final não pode ser anterior à data inicial.");
        }

        validarDataExpiracao(dataInicial);
        validarDataExpiracao(dataFinal);


        validarDataLimite(dataInicial, "Data inicial");
        validarDataLimite(dataFinal, "Data final");
    }

    private static void validarDataExpiracao(LocalDate data) {
        if (data.isBefore(LocalDate.now())) {
            throw new DataPassadaException("Datas anteriores à atual não são permitidas.");
        }
    }

    private static void validarDataLimite(LocalDate data, String tipo) {
        if (data.isAfter(LocalDate.now().plusDays(LIMITE_DIAS))) {
            throw new DataInvalidaException(tipo + " não pode ser superior a " + LIMITE_DIAS + " dias da data atual.");
        }
    }
}