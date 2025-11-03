package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.infrastructure.persistence.entity.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSalaRepository extends JpaRepository<SalaEntity, Long> {
    SalaEntity findByNumeroSala(Integer numeroSala);
}