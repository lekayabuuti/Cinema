package br.ifsp.demo.domain.exception;

public class DataInvalidaException extends RuntimeException{
    public DataInvalidaException(String mensagem){
        super(mensagem);
    }
}
