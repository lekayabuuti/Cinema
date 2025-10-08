package br.ifsp.demo.infrastructure.repository;

import br.ifsp.demo.infrastructure.entity.AssentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssentoRepository extends JpaRepository<AssentoEntity, Long> {
}