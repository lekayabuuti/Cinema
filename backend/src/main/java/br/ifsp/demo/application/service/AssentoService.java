package br.ifsp.demo.application.service;

import br.ifsp.demo.infrastructure.persistence.mapper.AssentoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.JpaAssentoRepository;
import org.springframework.stereotype.Service;

@Service
public class AssentoService {

    private final JpaAssentoRepository repository;

    public AssentoService(JpaAssentoRepository repository, AssentoMapper mapper) {
        this.repository = repository;
    }
    /*
    public List<Assento> buscarTodos() {
        return repository.findAll();
    }*/
}