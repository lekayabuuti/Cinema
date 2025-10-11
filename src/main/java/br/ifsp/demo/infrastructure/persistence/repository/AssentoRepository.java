package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.infrastructure.persistence.entity.AssentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssentoRepository extends JpaRepository<AssentoEntity, Long> {
}