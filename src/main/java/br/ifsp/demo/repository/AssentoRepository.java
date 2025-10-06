package br.ifsp.demo.repository;

import br.ifsp.demo.entity.AssentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssentoRepository extends JpaRepository<AssentoEntity, Long> {
}