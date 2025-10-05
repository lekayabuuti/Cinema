package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoService {
    private final SessaoRepository repository;

    public SessaoService(SessaoRepository repository) {
        this.repository = repository;
    }

    public List<Sessao> buscarSessoesEntre(LocalDate dataInicial, LocalDate dataFinal) {
        return repository.buscarEntreDatas(dataInicial,dataFinal);
    }
}
