package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.domain.model.Ingresso;
import br.ifsp.demo.domain.model.Sala;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.repository.SessaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class ReservaIngressoServiceTest {
    @Mock
    private SessaoRepository sessaoCinemaRepository;

    @InjectMocks
    private ReservaIngressoService reservaIngressoService;

    @Test
    @DisplayName("Deve reservar um ingresso com sucesso a sessão e o assento estão disponiveis")
    Sala sala01 = new Sala(1);
    Assento assentoA1 = new Assento("A", sala01);
    Ingresso ingressoDisponivel = new Ingresso(assentoA1, StatusIngresso.DISPONIVEL);
    Sessao sessaoTeste = new Sessao()

}