package br.ifsp.demo.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Horario(LocalDate data, LocalTime hora) {
}
