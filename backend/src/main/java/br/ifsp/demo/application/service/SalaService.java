package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Sala;
import br.ifsp.demo.infrastructure.persistence.entity.SalaEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SalaMapper;
import br.ifsp.demo.infrastructure.persistence.repository.JpaSalaRepository;
import org.springframework.stereotype.Service;

@Service
public class SalaService {

    private final JpaSalaRepository jpaSalaRepository;
    private final SalaMapper mapper;

    public SalaService(JpaSalaRepository jpaSalaRepository, SalaMapper salaMapper) {
        this.jpaSalaRepository = jpaSalaRepository;
        this.mapper = salaMapper;
    }

    public Sala buscarPorNumero(Integer numeroSala) {
        SalaEntity entity = jpaSalaRepository.findByNumeroSala(numeroSala);
        return mapper.toDomain(entity);
    }
}