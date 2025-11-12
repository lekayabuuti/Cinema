package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import br.ifsp.demo.infrastructure.service.SessaoServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AssentoSessaoService {

    private final AssentoSessaoRepository assentoSessaoRepository;
    private final SessaoServiceImpl sessaoServiceImpl;

    public AssentoSessaoService(AssentoSessaoRepository assentoSessaoRepository, SessaoServiceImpl sessaoServiceImpl) {
        this.assentoSessaoRepository = assentoSessaoRepository;
        this.sessaoServiceImpl = sessaoServiceImpl;
    }

    public List<AssentoSessao> buscarPorSessao(Long sessaoId, Boolean somenteDisponiveis) {

        if (sessaoId == null || sessaoId <= 0) {
            throw new IllegalArgumentException("O ID da sessão deve ser um número inteiro positivo.");
        }

        sessaoServiceImpl.buscarSessaoPorId(sessaoId);

        boolean apenasDisponiveis = somenteDisponiveis != null && somenteDisponiveis;

        if (apenasDisponiveis) {
            return assentoSessaoRepository.findBySessaoIdAndStatus(sessaoId, Status.DISPONIVEL);
        } else {

            return assentoSessaoRepository.findBySessaoId(sessaoId);
        }
    }
}
