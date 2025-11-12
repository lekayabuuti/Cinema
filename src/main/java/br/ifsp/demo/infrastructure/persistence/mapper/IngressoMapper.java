package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.model.Ingresso;
import br.ifsp.demo.infrastructure.persistence.entity.IngressoEntity;
import org.springframework.stereotype.Component;

@Component
public class IngressoMapper {

    private final SessaoMapper sessaoMapper;

    public IngressoMapper(SessaoMapper sessaoMapper) {
        this.sessaoMapper = sessaoMapper;
    }

    public IngressoEntity toEntity(Ingresso ingresso) {
        AssentoSessao assentoSessao = ingresso.getAssentoSessao();

        IngressoEntity entity = new IngressoEntity();
        entity.setAssento(assentoSessao.getAssento());
        entity.setStatus(assentoSessao.getStatus());
        entity.setSessao(sessaoMapper.toEntity(assentoSessao.getSessao()));
        // reserva será setada fora do mapper, no contexto da reserva
        return entity;
    }

    public Ingresso toDomain(IngressoEntity entity) {
        AssentoSessao assentoSessao = AssentoSessao.reconstituir(
                entity.getAssento(),
                sessaoMapper.toDomain(entity.getSessao()),
                entity.getStatus()
        );

        return new Ingresso(assentoSessao);
    }

}