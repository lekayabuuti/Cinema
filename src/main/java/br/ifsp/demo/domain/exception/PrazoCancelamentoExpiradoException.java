package br.ifsp.demo.domain.exception;

public class PrazoCancelamentoExpiradoException extends RuntimeException {
  public PrazoCancelamentoExpiradoException(String message) {
    super(message);
  }
}
