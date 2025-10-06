package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.exception.*;
import br.ifsp.demo.repository.*;
import br.ifsp.demo.mapper.SessaoMapper;
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
        validarDataLimite(dataInicial,"Data inicial");
        validarDataLimite(dataFinal,"Data final");

        validarDataDisponivel(dataInicial);
        validarDataDisponivel(dataFinal);

        return sessaoRepository.buscarEntreDatas(dataInicial,dataFinal)
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
}
