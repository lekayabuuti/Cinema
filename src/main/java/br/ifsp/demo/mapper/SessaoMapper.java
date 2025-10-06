package br.ifsp.demo.mapper;

import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.entity.SessaoEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SessaoMapper {
    public Sessao toDomain(SessaoEntity entity){
        Filme filme = new Filme(entity.getFilmeNome(), entity.getFilmeMinutos());
        Horario horario = new Horario(entity.getHorarioData(),entity.getHorarioHora());
        Sala sala = new Sala(entity.getNumeroSala());
        var ingressoDomain = entity.getIngressos().stream()
                .map(ingressoEntity -> new Ingresso(ingressoEntity.getAssento(), ingressoEntity.getStatus()))
                .collect(Collectors.toList());

        Sessao domain = new Sessao(filme, horario, ingressoDomain);
        domain.setId(entity.getId());
        return domain;
    }

    public SessaoEntity toEntity(Sessao domain){
        SessaoEntity entity = new SessaoEntity();
        if (domain.getId() != null){
            entity.setId(domain.getId());
        }
        entity.setFilmeNome(sessao.getFilme().nome());
        entity.setFilmeMinutos(sessao.getFilme().duracaoMinutos());
        entity.setHorarioData(sessao.getHorario().data());
        entity.setHorarioHora(sessao.getHorario().hora());
        entity.setNumeroSala(sessao.getSala().getNumeroSala());
        return entity;
    }

}
