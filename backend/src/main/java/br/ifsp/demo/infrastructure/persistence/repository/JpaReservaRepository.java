package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.infrastructure.persistence.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List; import java.util.UUID;


public interface JpaReservaRepository extends JpaRepository<ReservaEntity, Long> {
    @Query("SELECT r FROM ReservaEntity r " +
            "JOIN r.ingressos i " +
            "WHERE r.sessao.id = :sessaoId AND i.assentoSessao.usuario.id = :usuarioId")
    List<ReservaEntity> findBySessaoIdAndUsuarioId(@Param("sessaoId") Long sessaoId,
                                                   @Param("usuarioId") UUID usuarioId);
    List<ReservaEntity> findByUsuarioId(@Param("usuarioId") UUID usuarioId);



}
