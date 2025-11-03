package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.infrastructure.persistence.entity.DataIndisponivelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface JpaDataIndisponivelRepository extends JpaRepository<DataIndisponivelEntity, Long> {
    boolean existsByData(LocalDate data);
}