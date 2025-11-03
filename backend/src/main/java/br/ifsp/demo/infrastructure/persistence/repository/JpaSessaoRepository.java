package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface JpaSessaoRepository extends JpaRepository<SessaoEntity, Long> {

    List<SessaoEntity> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal);
}


