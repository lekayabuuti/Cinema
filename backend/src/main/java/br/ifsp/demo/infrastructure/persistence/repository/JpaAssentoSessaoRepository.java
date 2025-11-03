package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAssentoSessaoRepository extends JpaRepository<AssentoSessaoEntity, Long> {
    List<AssentoSessaoEntity> findBySessaoId(Long sessaoId);
}