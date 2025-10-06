package br.ifsp.demo.repository;

import br.ifsp.demo.entity.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<SalaEntity, Long> {
    SalaEntity findByNumeroSala(Integer numeroSala);
}