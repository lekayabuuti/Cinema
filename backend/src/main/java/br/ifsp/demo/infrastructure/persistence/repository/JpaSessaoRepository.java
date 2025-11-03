package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface JpaSessaoRepository extends JpaRepository<SessaoEntity, Long> {

    List<SessaoEntity> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal);

    Optional<SessaoEntity> findById(Long sessaoID);
}


