package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.domain.repository.SessaoRepository;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CancelamentoServiceTest {
    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private SessaoMapper sessaoMapper;

    @InjectMocks
    private CancelamentoService cancelamentoService;

    private Sessao sessaoDomain;
    private Long sessaoId;

    @BeforeEach
    void setUp(){
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));
        sala.getAssentos().add(new Assento("A2"));

        LocalDateTime agora = LocalDateTime.now();
        DataHora dataHoraFutura = new DataHora(agora.toLocalDate(), agora.toLocalTime().plusMinutes(30));

        sessaoDomain = new Sessao(
                new Filme("Filme legal", 90),
                dataHoraFutura,
                sala
        );

        sessaoDomain.reservarAssento("A1");
        sessaoDomain.reservarAssento("A2");

        SessaoEntity sessaoEntityFalsa = new SessaoEntity();
        sessaoEntityFalsa.setId(sessaoId);

    }

}