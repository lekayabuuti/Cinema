package br.ifsp.demo.infrastructure.mapper;

import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.infrastructure.entity.SessaoEntity;
import org.springframework.stereotype.Component;

@Component
public class SessaoMapper {
    public Sessao toDomain(SessaoEntity entity){
        Filme filme = new Filme(entity.getFilmeNome(), entity.getFilmeMinutos());
        Horario horario = new Horario(entity.getHorarioData(),entity.getHorarioHora());
        Sala sala = new Sala(entity.getNumeroSala());
        return new Sessao(filme,horario,sala);
    }

    public SessaoEntity toEntity(Sessao sessao){
        SessaoEntity entity = new SessaoEntity();
        entity.setFilmeNome(sessao.getFilme().nome());
        entity.setFilmeMinutos(sessao.getFilme().duracaoMinutos());
        entity.setHorarioData(sessao.getHorario().data());
        entity.setHorarioHora(sessao.getHorario().hora());
        entity.setNumeroSala(sessao.getSala().getNumeroSala());
        return entity;
    }

}
