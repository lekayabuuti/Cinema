package br.ifsp.demo.domain.repository;


import br.ifsp.demo.domain.model.Sessao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SessaoRepository {

    List<Sessao> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal);
    Optional<Sessao> findBySessaoId(Long sessaoID);
}


