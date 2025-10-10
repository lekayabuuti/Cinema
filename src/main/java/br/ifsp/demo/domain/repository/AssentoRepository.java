package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssentoRepository {
    Optional<Assento> findById(Long id);
    Assento save(Assento assento);
}