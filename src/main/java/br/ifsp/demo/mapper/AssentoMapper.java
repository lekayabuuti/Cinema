package br.ifsp.demo.mapper;

import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.entity.AssentoEntity;
import org.springframework.stereotype.Component;

@Component
public class AssentoMapper {

    private final SalaMapper mapper;

    public AssentoMapper(SalaMapper mapper) {
        this.mapper = mapper;
    }

    public Assento toDomain(AssentoEntity entity) {
        return new Assento(
                entity.getCodigo(),
                mapper.toDomain(entity.getSala())
        );
    }

    public AssentoEntity toEntity(Assento assento) {
        AssentoEntity entity = new AssentoEntity();
        entity.setCodigo(assento.getCodigo());
        entity.setSala(mapper.toEntity(assento.getSala()));
        return entity;
    }
}