package br.ifsp.demo.infrastructure.entity;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.Assento;
import jakarta.persistence.*;
import lombok.Data;

@Data // cria getters, setters, toString, etc. automaticamente
@Entity
@Table(name = "ingressos")
public class IngressoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded //vai dizr ao JPA para salvar os campos de Assento aqui.
    private Assento assento;

    @Enumerated(EnumType.STRING) //garante q status seja salvo como texto (ex: "DISPONIVEL")
    private Status status;

    @ManyToOne //relacionamento = muitos ingressos para Uma sessão
    @JoinColumn(name = "sessao_id", nullable = false)
    private SessaoEntity sessao;

    @ManyToOne // relacionamento = muitos ingressos para Uma reserva
    @JoinColumn(name = "reserva_id") // pode ser nulo, pq um ingresso pode não ter reserva ainda.
    private ReservaEntity reserva;
}