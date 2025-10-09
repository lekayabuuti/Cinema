package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Sala;
import br.ifsp.demo.infrastructure.entity.SalaEntity;
import br.ifsp.demo.infrastructure.mapper.SalaMapper;
import br.ifsp.demo.infrastructure.repository.SalaRepository;
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