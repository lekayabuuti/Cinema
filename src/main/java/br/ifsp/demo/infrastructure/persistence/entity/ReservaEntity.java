package br.ifsp.demo.infrastructure.persistence.entity;

import br.ifsp.demo.infrastructure.security.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reservas")
public class ReservaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //gerar IDs
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    private SessaoEntity sessao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngressoEntity> ingressos = new ArrayList<>();
}
