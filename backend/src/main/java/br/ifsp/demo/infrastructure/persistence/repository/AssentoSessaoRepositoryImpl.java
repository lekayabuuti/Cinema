package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import br.ifsp.demo.infrastructure.persistence.mapper.AssentoSessaoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AssentoSessaoRepositoryImpl implements AssentoSessaoRepository {

    private final JpaAssentoSessaoRepository jpaRepository;
    private final AssentoSessaoMapper mapper;

    public AssentoSessaoRepositoryImpl(JpaAssentoSessaoRepository jpaRepository, AssentoSessaoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AssentoSessao> findBySessaoId(Long sessaoId) {
        return jpaRepository.findBySessaoId(sessaoId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AssentoSessao> findByAssentoStatus(Status status) {
        return jpaRepository.findByStatus(status)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AssentoSessao> findBySessaoIdAndStatus(Long sessaoId, Status status) {
        return jpaRepository.findBySessaoIdAndStatus(sessaoId, status)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AssentoSessao> findByUsuarioIdAndStatus(UUID usuarioId, Status status) {
        return jpaRepository.findByUsuario_IdAndStatus(usuarioId, status)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }


}