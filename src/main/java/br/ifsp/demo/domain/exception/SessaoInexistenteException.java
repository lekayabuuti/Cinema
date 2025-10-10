package br.ifsp.demo.domain.exception;

public class SessaoInexistenteException extends RuntimeException{
    public SessaoInexistenteException(String message) {
        super(message);
    }
}
