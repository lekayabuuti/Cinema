package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.model.Sessao;
import java.time.LocalDate;
import java.util.List;

public interface SessaoService {
    List<Sessao> buscarSessoesEntre(LocalDate dataInicial, LocalDate dataFinal);
    Sessao buscarSessaoPorId(Long sessaoID);
}