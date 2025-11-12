package br.ifsp.demo.infrastructure.service;

import br.ifsp.demo.domain.exception.SessaoInativaException;
import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.IntervaloBusca;
import br.ifsp.demo.domain.repository.SessaoRepository;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.service.SessaoService;
import br.ifsp.demo.domain.service.ValidadorDataDisponivelService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoServiceImpl implements SessaoService {
    private final SessaoRepository sessaoRepository;
    private final ValidadorDataDisponivelService validator;


    public SessaoServiceImpl(SessaoRepository repository, ValidadorDataDisponivelService validadorDataDisponivelService) {
        this.sessaoRepository = repository;
        this.validator = validadorDataDisponivelService;
    }

    @Override
    public List<Sessao> buscarSessoesEntre(LocalDate dataInicial, LocalDate dataFinal) {
        IntervaloBusca periodo = new IntervaloBusca(dataInicial, dataFinal);

        validator.validar (periodo.dataInicial());
        validator.validar (periodo.dataFinal());

        return sessaoRepository.findByDataBetween(dataInicial, dataFinal);
    }

    @Override
    public Sessao buscarSessaoPorId(Long sessaoID) {
        Sessao sessao = sessaoRepository.findById(sessaoID)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada."));

        if (sessao.isEncerrada()){
            throw new SessaoInativaException("Sessao Encerrada para consultas!");
        }
        return sessao;
    }




}
