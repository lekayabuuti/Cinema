package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.exception.SessaoInativaException;
import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.IntervaloBusca;
import br.ifsp.demo.infrastructure.persistence.repository.DataIndisponivelRepository;
import br.ifsp.demo.domain.repository.SessaoRepository;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoService {
    private final SessaoRepository sessaoRepository;
    private final ValidadorDataDisponivelService validator;


    public SessaoService(SessaoRepository repository, ValidadorDataDisponivelService validadorDataDisponivelService) {
        this.sessaoRepository = repository;
        this.validator = validadorDataDisponivelService;
    }

    public List<Sessao> buscarSessoesEntre(LocalDate dataInicial, LocalDate dataFinal) {
        IntervaloBusca periodo = new IntervaloBusca(dataInicial, dataFinal);

        validator.validar (periodo.dataInicial());
        validator.validar (periodo.dataFinal());

        return sessaoRepository.findByDataBetween(dataInicial, dataFinal);
    }

    public Sessao buscarSessaoPorId(Long sessaoID) {
        Sessao sessao = sessaoRepository.findBySessaoId(sessaoID)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada."));

        if (sessao.isEncerrada()){
            throw new SessaoInativaException("Sessao Encerrada para consultas!");
        }
        return sessao;
    }




}
