package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.infrastructure.persistence.mapper.AssentoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.AssentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssentoService {

    private final AssentoRepository repository;

    public AssentoService(AssentoRepository repository, AssentoMapper mapper) {
        this.repository = repository;
    }
    /*
    public List<Assento> buscarTodos() {
        return repository.findAll();
    }*/
}