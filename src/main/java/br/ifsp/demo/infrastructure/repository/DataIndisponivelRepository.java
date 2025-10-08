package br.ifsp.demo.infrastructure.repository;

import br.ifsp.demo.infrastructure.entity.DataIndisponivelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface DataIndisponivelRepository extends JpaRepository<DataIndisponivelEntity, Long> {
    boolean existsByData(LocalDate data);
}