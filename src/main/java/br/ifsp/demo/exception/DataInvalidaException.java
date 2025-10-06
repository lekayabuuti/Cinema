package br.ifsp.demo.exception;

public class DataInvalidaException extends RuntimeException{
    public DataInvalidaException(String mensagem){
        super(mensagem);
    }
}
