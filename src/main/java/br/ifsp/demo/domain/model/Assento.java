package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.enumerations.Status;
import jakarta.persistence.Embeddable;
import lombok.*;

@Value
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Construtor vazio para o JPA
@AllArgsConstructor
@Data
public class Assento {
    private final String codigo;
}

