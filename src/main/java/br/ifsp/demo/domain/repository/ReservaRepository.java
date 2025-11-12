package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Reserva;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservaRepository {
    Reserva salvar(Reserva reserva);
    Optional<Reserva> buscarPorId(Long id);
    List<Reserva> buscarPorSessaoEUsuario(Long sessaoId, UUID usuarioId);
    List<Reserva> buscarPorUsuario(UUID usuarioId);
}