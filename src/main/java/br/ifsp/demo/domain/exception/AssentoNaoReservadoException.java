package br.ifsp.demo.domain.exception;

public class AssentoNaoReservadoException extends RuntimeException {
    public AssentoNaoReservadoException(String message) {
        super(message);
    }
}
