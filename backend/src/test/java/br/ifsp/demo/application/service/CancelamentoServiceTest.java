package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.*;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.JpaSessaoRepository;
import br.ifsp.demo.infrastructure.security.auth.AuthenticationInfoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class BuscarReservaServiceTest {
    @Mock
    private JpaSessaoRepository jpaSessaoRepository;

    @Mock
    private SessaoMapper sessaoMapper;

    @Mock
    private AuthenticationInfoService authService;

    @InjectMocks
    private CancelamentoService cancelamentoService;

    private Sessao sessaoDomain;
    private SessaoEntity sessaoEntityFalsa;
    private Long sessaoId = 1L;
    private UUID dummyUserId = UUID.randomUUID();

    @BeforeEach
    void setUp(){
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));
        sala.getAssentos().add(new Assento("A2"));

        LocalDateTime agora = LocalDateTime.now();
        DataHora dataHoraFutura = new DataHora(agora.toLocalDate(), agora.toLocalTime().plusMinutes(30));

        sessaoDomain = new Sessao(
                new Filme("Filme legal", 90),
                //Usamos uma data bem no futuro (+100 dias) para garantir que os testes de 'caminho feliz'
                // não se tornem frágeis e quebrem com o passar do tempo.
                //É possível aumentar ainda mais essa data, caso atinja esse tempo e o código quebre.
                new DataHora(LocalDate.of(2025, 10, 9).plusDays(100), LocalTime.of(20,0)),
                sala
        );

        sessaoDomain.reservarAssento("A1", dummyUserId);
        sessaoDomain.reservarAssento("A2", dummyUserId);

        sessaoEntityFalsa = new SessaoEntity();
        sessaoEntityFalsa.setId(sessaoId);
    }

    @Test
    @DisplayName("Deve cancelar uma reserva com sucesso se prazo de cancelamento não expirou")
    void deveCancelarUmaReservaComSucessoSePrazoDeCancelamentoNaoExpirou(){
        String assentoParaCancelar = "A1";

        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
        when(sessaoMapper.toEntity(sessaoDomain)).thenReturn(new SessaoEntity());
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId); //MOCKAR AUTHSERVICE

        cancelamentoService.cancelar(sessaoId, assentoParaCancelar);

        verify(jpaSessaoRepository).save(any(SessaoEntity.class));

        AssentoSessao assentoCancelado = sessaoDomain.getAssentosDaSessao().getFirst();
        assertThat(assentoCancelado.estaDisponivel()).isTrue();
    }

    @Test
    @DisplayName("Deve lançar TempoDeCancelamentoExcedidoException quando o prazo de cancelamento expirar")
    void deveLancarTempoDeCancelamentoExcedidoExceptionQuandoPrazoDeCancelamentoExpirar(){
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));

        DataHora dataHoraPassada = new DataHora(LocalDate.of(2024, 1, 1), LocalTime.of(20, 0));

        Sessao sessaoQuaseComecando = new Sessao(
                new Filme("Filme legal", 90),
                dataHoraPassada,
                sala
        );
        AssentoSessao assentoParaReservarManualmente = sessaoQuaseComecando.getAssentosDaSessao().get(0);
        assentoParaReservarManualmente.reservar(dummyUserId);

        String assentoParaCancelar = "A1";

        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(new SessaoEntity()));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoQuaseComecando);
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId);

        assertThrows(TempoDeCancelamentoExcedidoException.class, () -> {
            cancelamentoService.cancelar(sessaoId, assentoParaCancelar);
        });

        verify(jpaSessaoRepository, never()).save(any());
    }



    @Test
    @DisplayName("Deve lançar ReservaInexistenteException ao tentar cancelar um assento não reservado")
    void deveLancarReservaInexistenteExceptionQuandoAssentoNaoEstaReservado() {
        // cria um cenario onde a sessão existe, mas o assento "A2" esta disponível
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));
        sala.getAssentos().add(new Assento("A2"));

        Sessao sessaoComVagas = new Sessao(
                new Filme("Filme com Vagas", 120),
                //Usamos uma data bem no futuro (+100 dias) para garantir que os testes de 'caminho feliz'
                // não se tornem frágeis e quebrem com o passar do tempo.
                //É possível aumentar ainda mais essa data, caso atinja esse tempo e o código quebre.
                new DataHora(LocalDate.of(2025, 10, 9).plusDays(100), LocalTime.of(20,0)),
                sala
        );
        // reserva somente A1, deixando A2 livre
        sessaoComVagas.reservarAssento("A1", dummyUserId);

        String assentoNaoReservado = "A2";

        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(new SessaoEntity()));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoComVagas);
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId);

        assertThrows(ReservaInexistenteException.class, () -> {
            cancelamentoService.cancelar(sessaoId, assentoNaoReservado);
        });

        // garantir que a operação foi abortada antes de salvar
        verify(jpaSessaoRepository, never()).save(any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("Deve lançar IllegalArgumentException ao cancelar com código de assento inválido")
    void deveLancarIllegalArgumentExceptionQuandoCodigoAssentoForInvalido(String assentoInvalido) {
        assertThrows(IllegalArgumentException.class, () -> {
            cancelamentoService.cancelar(1L, assentoInvalido);
        });

        verifyNoInteractions(jpaSessaoRepository, sessaoMapper, authService);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o ID da sessão for nulo")
    void deveLancarIllegalArgumentExceptionQuandoSessaoIdForNulo() {
        Long idNulo = null;
        String qualquerAssento = "A1";

        assertThrows(IllegalArgumentException.class, () -> {
            cancelamentoService.cancelar(idNulo, qualquerAssento);
        });

        verifyNoInteractions(jpaSessaoRepository, sessaoMapper, authService);
    }
}