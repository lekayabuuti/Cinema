package br.ifsp.demo.exception;

public class SessaoIndisponivelException extends RuntimeException {
    public SessaoIndisponivelException(String message) {
        super(message);
    }
}
