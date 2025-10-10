package br.ifsp.demo.domain.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e){
        final HttpStatus badRequest = BAD_REQUEST;
        final ApiException apiException = ApiException.builder()
                .status(badRequest)
                .message(e.getMessage())
                .developerMessage(e.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        final HttpStatus badRequest = BAD_REQUEST;
        final ApiException apiException = ApiException.builder()
                .status(badRequest)
                .message(e.getMessage())
                .developerMessage(e.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e){
        final HttpStatus forbidden = FORBIDDEN;
        final ApiException apiException = ApiException.builder()
                .status(forbidden)
                .message(e.getMessage())
                .developerMessage(e.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(apiException, forbidden);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e){
        final HttpStatus notFound = NOT_FOUND;
        final ApiException apiException = ApiException.builder()
                .status(notFound)
                .message(e.getMessage())
                .developerMessage(e.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException e){
        final HttpStatus conflict = CONFLICT;
        final ApiException apiException = ApiException.builder()
                .status(conflict)
                .message(e.getMessage())
                .developerMessage(e.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<?> handleDataInvalidaException(DataInvalidaException ex) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ApiException apiException = ApiException.builder()
                .status(badRequest)
                .message(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(SessaoIndisponivelException.class)
    public ResponseEntity<?> handleSessaoIndisponivelException(SessaoIndisponivelException ex) {
        final HttpStatus badRequest = BAD_REQUEST;

        final ApiException apiException = ApiException.builder()
                .status(badRequest)
                .message(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(DataPassadaException.class)
    public ResponseEntity<?> handleDataPassadaException(DataPassadaException ex) {
        final HttpStatus badRequest = BAD_REQUEST;

        final ApiException apiException = ApiException.builder()
                .status(badRequest)
                .message(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(SessaoInativaException.class)
    public ResponseEntity<?> handleSessaoInativaException(SessaoInativaException ex) {
        final HttpStatus badRequest = BAD_REQUEST;

        final ApiException apiException = ApiException.builder()
                .status(badRequest)
                .message(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(SessaoInexistenteException.class)
    public ResponseEntity<?> handleSessaoInexistenteException(SessaoInativaException ex) {
        final HttpStatus badRequest = BAD_REQUEST;

        final ApiException apiException = ApiException.builder()
                .status(badRequest)
                .message(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, badRequest);
    }
}
