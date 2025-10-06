package br.ifsp.demo.repository;

import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.entity.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface SessaoRepository extends JpaRepository<SessaoEntity, Long> {

    List<SessaoEntity> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal);
}
