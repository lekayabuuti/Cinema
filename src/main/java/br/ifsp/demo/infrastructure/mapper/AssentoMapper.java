package br.ifsp.demo.infrastructure.mapper;

import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.infrastructure.entity.AssentoEntity;
import org.springframework.stereotype.Component;

@Component
public class AssentoMapper {
    public Assento toDomain(AssentoEntity entity) {
        return new Assento(
                entity.getCodigo()
        );
    }

    public AssentoEntity toEntity(Assento assento) {
        AssentoEntity entity = new AssentoEntity();
        entity.setCodigo(assento.getCodigo());;
        return entity;
    }
}