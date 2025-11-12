package br.ifsp.demo.domain.exception;

public class SessaoIndisponivelException extends RuntimeException {
    public SessaoIndisponivelException(String message) {
        super(message);
    }
}
