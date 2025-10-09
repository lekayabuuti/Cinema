package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.AssentoSessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.AssentoSessaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssentoSessaoService {

    private final AssentoSessaoRepository repository;
    private final AssentoSessaoMapper mapper;

    public AssentoSessaoService(AssentoSessaoRepository repository, AssentoSessaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AssentoSessao> buscarPorSessao(Long sessaoId) {
        List<AssentoSessaoEntity> entidades = repository.findBySessaoId(sessaoId);

        return entidades.stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<AssentoSessao> buscarAssentosDisponiveis(Long sessaoId, Status status) {
        List<AssentoSessaoEntity> entidades = repository.findBySessaoId(sessaoId);

        return entidades.stream()
                .filter(e -> e.getStatus().equals(status))
                .map(mapper::toDomain)
                .toList();
    }
}