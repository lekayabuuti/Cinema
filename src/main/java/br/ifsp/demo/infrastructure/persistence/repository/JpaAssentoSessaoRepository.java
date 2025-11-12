package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaAssentoSessaoRepository extends JpaRepository<AssentoSessaoEntity, Long> {
    List<AssentoSessaoEntity> findBySessaoId(Long sessaoId);
    List<AssentoSessaoEntity> findByStatus(Status status);
    List<AssentoSessaoEntity> findBySessaoIdAndStatus(Long sessaoId, Status status);
    List<AssentoSessaoEntity> findByUsuario_IdAndStatus(UUID usuarioId, Status status);
}