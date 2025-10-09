package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.model.Sala;
import br.ifsp.demo.infrastructure.persistence.entity.SalaEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SalaMapper;
import br.ifsp.demo.infrastructure.persistence.repository.SalaRepository;
import org.springframework.stereotype.Service;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper mapper;

    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper) {
        this.salaRepository = salaRepository;
        this.mapper = salaMapper;
    }

    public Sala buscarPorNumero(Integer numeroSala) {
        SalaEntity entity = salaRepository.findByNumeroSala(numeroSala);
        return mapper.toDomain(entity);
    }
}