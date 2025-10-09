package br.ifsp.demo.domain.repository;


import br.ifsp.demo.domain.model.Sessao;

import java.time.LocalDate;
import java.util.List;

public interface SessaoRepository {

    List<Sessao> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal);
}


