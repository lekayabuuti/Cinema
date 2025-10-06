package br.ifsp.demo.mapper;

import br.ifsp.demo.domain.model.Sala;
import br.ifsp.demo.entity.SalaEntity;

public class SalaMapper {

    public Sala toDomain(SalaEntity entity) {
        return new Sala(entity.getNumeroSala());
    }

    public SalaEntity toEntity(Sala sala) {
        return new SalaEntity(sala.getNumeroSala());
    }
}