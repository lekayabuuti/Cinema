package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssentoSessaoRepository extends JpaRepository<AssentoSessaoEntity, Long> {
    List<AssentoSessaoEntity> findBySessaoId(Long sessaoId);
    List<AssentoSessaoEntity> findByUserIdAndStatus(UUID userId, Status status);
}