package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.model.Reserva;
import br.ifsp.demo.domain.repository.ReservaRepository;
import br.ifsp.demo.infrastructure.persistence.entity.ReservaEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.ReservaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReservaRepositoryImpl implements ReservaRepository {

    private final JpaReservaRepository jpaReservaRepository;
    private final ReservaMapper reservaMapper;

    public ReservaRepositoryImpl(JpaReservaRepository jpaReservaRepository, ReservaMapper reservaMapper) {
        this.jpaReservaRepository = jpaReservaRepository;
        this.reservaMapper = reservaMapper;
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        ReservaEntity entity = reservaMapper.toEntity(reserva);
        ReservaEntity saved = jpaReservaRepository.save(entity);
        return reservaMapper.toDomain(saved);
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return jpaReservaRepository.findById(id)
                .map(reservaMapper::toDomain);
    }

    @Override
    public List<Reserva> buscarPorSessaoEUsuario(Long sessaoId, UUID usuarioId) {
        return jpaReservaRepository.findBySessaoIdAndUsuarioId(sessaoId, usuarioId)
                .stream()
                .map(reservaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reserva> buscarPorUsuario(UUID usuarioId) {
        return jpaReservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(reservaMapper::toDomain)
                .toList();
    }
}