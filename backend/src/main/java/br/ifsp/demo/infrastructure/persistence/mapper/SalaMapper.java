package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.Sala;
import br.ifsp.demo.infrastructure.persistence.entity.SalaEntity;

public class SalaMapper {

    public Sala toDomain(SalaEntity entity) {
        return new Sala(entity.getNumeroSala());
    }

    public SalaEntity toEntity(Sala sala) {
        return new SalaEntity(sala.getNumeroSala());
    }
}