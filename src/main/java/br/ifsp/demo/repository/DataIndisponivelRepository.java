package br.ifsp.demo.repository;

import br.ifsp.demo.entity.DataIndisponivelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface DataIndisponivelRepository extends JpaRepository<DataIndisponivelEntity, Long> {
    boolean existsByData(LocalDate data);
}