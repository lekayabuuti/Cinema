package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;


    public List<Sessao> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return sessaoRepository.findByDataBetween(inicio, fim);
    }

    public Sessao buscarPorId(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    }


}