package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import org.springframework.stereotype.Component;

@Component
public class SessaoMapper {
    public Sessao toDomain(SessaoEntity entity){
        Filme filme = new Filme(entity.getFilmeNome(), entity.getFilmeMinutos());
        DataHora dataHora = new DataHora(entity.getData(),entity.getHorario());
        Sala sala = new Sala(entity.getNumeroSala());
        return new Sessao(filme, dataHora,sala);
    }

    public SessaoEntity toEntity(Sessao sessao){
        SessaoEntity entity = new SessaoEntity();
        entity.setFilmeNome(sessao.getFilme().nome());
        entity.setFilmeMinutos(sessao.getFilme().duracaoMinutos());
        entity.setData(sessao.getDataHora().data());
        entity.setHorario(sessao.getDataHora().hora());
        entity.setNumeroSala(sessao.getSala().getNumeroSala());
        return entity;
    }

}
