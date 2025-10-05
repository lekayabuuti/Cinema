package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.exception.*;
import br.ifsp.demo.repository.SessaoRepository;
import br.ifsp.demo.mapper.SessaoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoService {
    private final SessaoRepository repository;
    private final SessaoMapper mapper;
    private static final int LIMITE_DIAS = 7;

    public SessaoService(SessaoRepository repository, SessaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Sessao> buscarSessoesEntre(LocalDate dataInicial, LocalDate dataFinal) {
        validarData(dataInicial,"Data inicial");
        validarData(dataFinal,"Data final");

        return repository.buscarEntreDatas(dataInicial,dataFinal)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    private void validarData(LocalDate data, String tipo) {
        if (data.isAfter(LocalDate.now().plusDays(LIMITE_DIAS))) {
            throw new DataInvalidaException(tipo + " não pode ser superior a " + LIMITE_DIAS + " dias da data atual.");
        }
    }
}
