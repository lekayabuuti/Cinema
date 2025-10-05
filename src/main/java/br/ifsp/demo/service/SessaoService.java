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

    public SessaoService(SessaoRepository repository, SessaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Sessao> buscarSessoesEntre(LocalDate dataInicial, LocalDate dataFinal) {
        if(dataInicial.isAfter(LocalDate.now().plusDays(7))){
            throw new DataInvalidaException("Data inicial não pode ser superior a 7 dias da data atual.");
        }
        return repository.buscarEntreDatas(dataInicial,dataFinal)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
