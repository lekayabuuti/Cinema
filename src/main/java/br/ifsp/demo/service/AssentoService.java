package br.ifsp.demo.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.entity.AssentoEntity;
import br.ifsp.demo.mapper.AssentoMapper;
import br.ifsp.demo.repository.AssentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssentoService {

    private final AssentoRepository repository;
    private final AssentoMapper mapper;

    public AssentoService(AssentoRepository repository, AssentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Assento> buscarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}