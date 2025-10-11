package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import br.ifsp.demo.domain.service.SessaoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssentoSessaoService {

    private final AssentoSessaoRepository assentoSessaoRepository;
    private final SessaoService sessaoService;

    public AssentoSessaoService(AssentoSessaoRepository assentoSessaoRepository, SessaoService sessaoService) {
        this.assentoSessaoRepository = assentoSessaoRepository;
        this.sessaoService = sessaoService;
    }

    public List<AssentoSessao> buscarPorSessao(Long sessaoId, Boolean somenteDisponiveis) {

        if (sessaoId == null || sessaoId <= 0) {
            throw new IllegalArgumentException("O ID da sessão deve ser um número inteiro positivo.");
        }

        sessaoService.buscarSessaoPorId(sessaoId);

        boolean apenasDisponiveis = somenteDisponiveis != null && somenteDisponiveis;

        if (apenasDisponiveis) {
            return assentoSessaoRepository.findBySessaoIdAndStatus(sessaoId, Status.DISPONIVEL);
        } else {

            return assentoSessaoRepository.findBySessaoId(sessaoId);
        }
    }
}
