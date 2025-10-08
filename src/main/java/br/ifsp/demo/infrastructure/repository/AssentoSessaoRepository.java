package br.ifsp.demo.infrastructure.repository;

import br.ifsp.demo.infrastructure.entity.AssentoSessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssentoSessaoRepository extends JpaRepository<AssentoSessaoEntity, Long> {
    List<AssentoSessaoEntity> findBySessaoId(Long sessaoId);
}