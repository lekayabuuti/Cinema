package br.ifsp.demo.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assentos")
public class AssentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    public AssentoEntity() {}

    public AssentoEntity(String codigo) {
        this.codigo = codigo;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }


}