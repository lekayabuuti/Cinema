package br.ifsp.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Horario(LocalDate data, LocalTime hora) {
}
