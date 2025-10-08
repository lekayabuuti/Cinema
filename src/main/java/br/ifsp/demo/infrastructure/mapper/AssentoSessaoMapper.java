package br.ifsp.demo.infrastructure.mapper;

import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.infrastructure.entity.AssentoSessaoEntity;
import org.springframework.stereotype.Component;

@Component
public class AssentoSessaoMapper {

    private final AssentoMapper assentoMapper;
    private final SessaoMapper sessaoMapper;

    public AssentoSessaoMapper(AssentoMapper assentoMapper, SessaoMapper sessaoMapper) {
        this.assentoMapper = assentoMapper;
        this.sessaoMapper = sessaoMapper;
    }

    public AssentoSessao toDomain(AssentoSessaoEntity entity) {
        AssentoSessao assentoSessao = new AssentoSessao(
                assentoMapper.toDomain(entity.getAssento()),
                sessaoMapper.toDomain(entity.getSessao())
        );
        assentoSessao.setStatus(entity.getStatus());
        return assentoSessao;
    }

    public AssentoSessaoEntity toEntity(AssentoSessao domain) {
        AssentoSessaoEntity entity = new AssentoSessaoEntity();
        entity.setAssento(assentoMapper.toEntity(domain.getAssento()));
        entity.setSessao(sessaoMapper.toEntity(domain.getSessao()));
        entity.setStatus(domain.getStatus());
        return entity;
    }
}