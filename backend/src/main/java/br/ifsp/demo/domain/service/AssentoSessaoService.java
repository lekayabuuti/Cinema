package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import br.ifsp.demo.infrastructure.service.SessaoServiceImpl;

import java.util.List;


public class AssentoSessaoService {

    private final AssentoSessaoRepository repository;
    private final SessaoServiceImpl sessaoServiceImpl;

    public AssentoSessaoService(AssentoSessaoRepository repository, SessaoServiceImpl sessaoServiceImpl) {
        this.repository = repository;

        this.sessaoServiceImpl = sessaoServiceImpl;
    }

    public List<AssentoSessao> buscarPorSessao(Long sessaoId) {
        Sessao sessaoValidacao = sessaoServiceImpl.buscarSessaoPorId(sessaoId);

        return repository.findBySessaoId(sessaoId);
    }

    public List<AssentoSessao> buscarAssentosPorStatus( Status status) {
        return repository.findByAssentoStatus(status);
    }
}