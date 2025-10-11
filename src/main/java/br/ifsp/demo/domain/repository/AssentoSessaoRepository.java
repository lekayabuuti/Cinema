package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssentoSessaoRepository {
    List<AssentoSessao> findBySessaoId(Long sessaoId);
    List<AssentoSessao> findByAssentoStatus(Status status);
    List<AssentoSessao> findBySessaoIdAndStatus(Long sessaoId, Status status);
}