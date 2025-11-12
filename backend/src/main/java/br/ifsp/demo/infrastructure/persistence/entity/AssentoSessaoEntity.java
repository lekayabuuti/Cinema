package br.ifsp.demo.infrastructure.persistence.entity;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.infrastructure.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "assentos_sessao")
public class AssentoSessaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assento_id", nullable = false)
    private AssentoEntity assento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_id", nullable = false)
    private SessaoEntity sessao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public AssentoSessaoEntity() {}

    public AssentoSessaoEntity(AssentoEntity assento, SessaoEntity sessao, Status status) {
        this.assento = assento;
        this.sessao = sessao;
        this.status = status;
    }

}