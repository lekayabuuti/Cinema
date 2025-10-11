package br.ifsp.demo.infrastructure.persistence.entity;

import br.ifsp.demo.domain.enumerations.Status;
import jakarta.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public AssentoSessaoEntity() {}

    public AssentoSessaoEntity(AssentoEntity assento, SessaoEntity sessao, Status status) {
        this.assento = assento;
        this.sessao = sessao;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AssentoEntity getAssento() { return assento; }
    public void setAssento(AssentoEntity assento) { this.assento = assento; }

    public SessaoEntity getSessao() { return sessao; }
    public void setSessao(SessaoEntity sessao) { this.sessao = sessao; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}