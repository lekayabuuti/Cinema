package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.DataInvalidaException;
import br.ifsp.demo.domain.exception.DataPassadaException;
import br.ifsp.demo.domain.exception.SessaoIndisponivelException;
import br.ifsp.demo.infrastructure.repository.DataIndisponivelRepository;
import br.ifsp.demo.infrastructure.repository.SessaoRepository;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.infrastructure.mapper.SessaoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoService {
    private final SessaoMapper mapper;
    private final SessaoRepository sessaoRepository;
    private final DataIndisponivelRepository dataIndisponivelRepository;
    private static final int LIMITE_DIAS = 7;


    public SessaoService(SessaoRepository repository, SessaoMapper mapper, DataIndisponivelRepository dataIndisponivelRepository) {
        this.sessaoRepository = repository;
        this.mapper = mapper;
        this.dataIndisponivelRepository = dataIndisponivelRepository;
    }

    public List<Sessao> buscarSessoesEntre(LocalDate dataInicial, LocalDate dataFinal) {
        validacao(dataInicial, dataFinal);

        return sessaoRepository.findByDataBetween(dataInicial,dataFinal)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }


    private void validarDataLimite(LocalDate data, String tipo) {
        if (data.isAfter(LocalDate.now().plusDays(LIMITE_DIAS))) {
            throw new DataInvalidaException(tipo + " não pode ser superior a " + LIMITE_DIAS + " dias da data atual.");
        }
    }

    private void validarDataDisponivel(LocalDate data) {
        if(dataIndisponivelRepository.existsByData(data)){
            throw new SessaoIndisponivelException("Data "+ data +" indisponível para sessões");
        }
    }

    private void validarDataExpiracao(LocalDate data) {
        if (data.isBefore(LocalDate.now())) {
            throw new DataPassadaException("Datas anteriores à atual não são permitidas.");
        }
    }


    private void validacao(LocalDate dataInicial, LocalDate dataFinal) {
        if(dataInicial.isAfter(dataFinal)) {
            throw new DataInvalidaException("Data final não pode ser anterior à data inicial.");
        }

        validarDataLimite(dataInicial,"Data inicial");
        validarDataLimite(dataFinal,"Data final");

        validarDataDisponivel(dataInicial);
        validarDataDisponivel(dataFinal);

        validarDataExpiracao(dataInicial);
        validarDataExpiracao(dataFinal);
    }

}
