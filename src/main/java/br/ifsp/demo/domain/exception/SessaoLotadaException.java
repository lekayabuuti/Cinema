package br.ifsp.demo.domain.exception;

public class SessaoLotadaException extends RuntimeException {
    public SessaoLotadaException(String message) {
        super(message);
    }
}
