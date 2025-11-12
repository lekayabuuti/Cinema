package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.Reserva;
import br.ifsp.demo.infrastructure.persistence.entity.ReservaEntity;
import br.ifsp.demo.infrastructure.persistence.entity.IngressoEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ReservaMapper {

    private final SessaoMapper sessaoMapper;
    private final IngressoMapper ingressoMapper;

    public ReservaMapper(SessaoMapper sessaoMapper, IngressoMapper ingressoMapper) {
        this.sessaoMapper = sessaoMapper;
        this.ingressoMapper = ingressoMapper;
    }

    public ReservaEntity toEntity(Reserva reserva) {
        ReservaEntity entity = new ReservaEntity();
        entity.setId(reserva.getId());
        entity.setSessao(sessaoMapper.toEntity(reserva.getSessao()));
        entity.setIngressos(
                reserva.getIngressos().stream()
                        .map(ingresso -> {
                            IngressoEntity ingressoEntity = ingressoMapper.toEntity(ingresso);
                            ingressoEntity.setReserva(entity); // vincula a reserva
                            return ingressoEntity;
                        })
                        .collect(Collectors.toList())
        );
        return entity;
    }

    public Reserva toDomain(ReservaEntity entity) {
        Reserva reserva = new Reserva();
        reserva.setId(entity.getId());
        reserva.setSessao(sessaoMapper.toDomain(entity.getSessao()));
        reserva.setIngressos(
                entity.getIngressos().stream()
                        .map(ingressoMapper::toDomain)
                        .collect(Collectors.toList())
        );
        return reserva;
    }
}