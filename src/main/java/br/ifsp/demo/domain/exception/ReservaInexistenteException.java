package br.ifsp.demo.domain.exception;

public class ReservaInexistenteException extends RuntimeException {
    public ReservaInexistenteException(String message) {
        super(message);
    }
}
