package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssentoSessaoService {

    private final AssentoSessaoRepository repository;

    public AssentoSessaoService(AssentoSessaoRepository repository) {
        this.repository = repository;

    }

    public List<AssentoSessao> buscarPorSessao(Long sessaoId) {
        return repository.findBySessaoId(sessaoId);
    }

    public List<AssentoSessao> buscarAssentosPorStatus( Status status) {
        return repository.findByAssentoStatus(status);
    }
}