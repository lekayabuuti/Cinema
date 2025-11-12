package br.ifsp.demo.domain.exception;

public class AssentoInexistenteException extends RuntimeException {
    public AssentoInexistenteException(String message) {
        super(message);
    }
}
