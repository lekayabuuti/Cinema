package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.domain.repository.ReservaRepository;
import br.ifsp.demo.domain.repository.SessaoRepository;
import br.ifsp.demo.infrastructure.security.auth.AuthenticationInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock SessaoRepository sessaoRepository;
    @Mock ReservaRepository reservaRepository;
    @Mock AuthenticationInfoService authService;

    @InjectMocks ReservaService reservaService;

    // Auxiliares
    private UUID autenticarUsuario() {
        UUID usuarioId = UUID.randomUUID();
        when(authService.getAuthenticatedUserId()).thenReturn(usuarioId);
        return usuarioId;
    }

    private void mockSessaoExistente(Long sessaoId, Sessao sessao) {
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessao));
    }

    private void mockSessaoInexistente(Long sessaoId) {
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.empty());
    }

    //31
    @Test
    @DisplayName("Deve retornar reservas do usuário em múltiplas sessões")
    void deveRetornarReservasDoUsuarioEmMultiplasSessoes() {
        UUID usuarioId = autenticarUsuario();
        List<Long> sessoesId = List.of(1L, 2L);
        Reserva r1 = mock(Reserva.class), r2 = mock(Reserva.class), r3 = mock(Reserva.class);

        // Mockando existência das sessões
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(mock(Sessao.class)));
        when(sessaoRepository.findById(2L)).thenReturn(Optional.of(mock(Sessao.class)));

        when(reservaRepository.buscarPorSessaoEUsuario(1L, usuarioId)).thenReturn(List.of(r1));
        when(reservaRepository.buscarPorSessaoEUsuario(2L, usuarioId)).thenReturn(List.of(r2, r3));

        List<Reserva> resultado = reservaService.buscarReservasPorSessoesDoUsuario(sessoesId);

        assertEquals(3, resultado.size());
        assertTrue(resultado.containsAll(List.of(r1, r2, r3)));
    }

    //49
    @Test
    @DisplayName("Deve reservar ingresso com sucesso")
    void deveReservarIngressoComSucesso() {
        Long sessaoId = 1L;
        String codigo = "A1";
        UUID usuarioId = autenticarUsuario();

        Sessao sessao = mock(Sessao.class);
        Assento assento = mock(Assento.class);
        AssentoSessao assentoSessao = AssentoSessao.criarNovo(assento, sessao);
        assentoSessao.reservar(usuarioId);

        mockSessaoExistente(sessaoId, sessao);
        when(sessao.reservarAssento(codigo, usuarioId)).thenReturn(assentoSessao);
        when(reservaRepository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        Ingresso ingresso = reservaService.reservarIngresso(sessaoId, codigo);

        assertEquals(assentoSessao, ingresso.getAssentoSessao());
    }

    //52//54
    @Test
    @DisplayName("Deve lançar exceção quando ID da sessão for nulo")
    void deveLancarExcecaoQuandoIDSessaoForNulo() {
        assertThrows(NullPointerException.class, () -> {
            reservaService.buscarReservasPorSessaoEUsuario(null, UUID.randomUUID());
        });
    }

    //56
    @Test
    @DisplayName("Deve lançar exceção quando ID da sessão for vazio")
    void deveLancarExcecaoQuandoIDSessaoForVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            reservaService.buscarReservasPorSessaoEUsuario(0L, UUID.randomUUID());
        });
    }

    //32
    @Test
    @DisplayName("Deve retornar lista vazia quando usuário não possui reservas")
    void deveRetornarListaVaziaQuandoUsuarioNaoPossuiReservas() {
        UUID usuarioId = autenticarUsuario();
        when(reservaRepository.buscarPorUsuario(usuarioId)).thenReturn(Collections.emptyList());

        List<Reserva> reservas = reservaService.buscarTodasReservasDoUsuario();

        assertTrue(reservas.isEmpty());
    }

    //58
    @Test
    @DisplayName("Deve lançar exceção quando sessão não existe")
    void deveLancarExcecaoQuandoSessaoNaoExiste() {
        Long sessaoId = 999L;
        UUID usuarioId = UUID.randomUUID();
        mockSessaoInexistente(sessaoId);

        assertThrows(SessaoInexistenteException.class, () -> {
            reservaService.buscarReservasPorSessaoEUsuario(sessaoId, usuarioId);
        });
    }

    //60
    @Test
    @DisplayName("Deve lançar exceção ao buscar reservas em múltiplas sessões incluindo uma inexistente")
    void deveLancarExcecaoAoBuscarReservasEmMultiplasSessoesComInexistente() {
        UUID usuarioId = autenticarUsuario();
        List<Long> sessoesId = List.of(1L, 2L);

        mockSessaoExistente(1L, mock(Sessao.class));
        mockSessaoInexistente(2L);
        when(reservaRepository.buscarPorSessaoEUsuario(1L, usuarioId)).thenReturn(List.of(mock(Reserva.class)));

        assertThrows(SessaoInexistenteException.class, () -> {
            reservaService.buscarReservasPorSessoesDoUsuario(sessoesId);
        });
    }
}