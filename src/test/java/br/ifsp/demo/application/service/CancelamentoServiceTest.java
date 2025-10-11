package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.*;
import br.ifsp.demo.application.service.ReservaIngressoService;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.SessaoRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class CancelamentoServiceTest {
    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private SessaoMapper sessaoMapper;

    @InjectMocks
    private CancelamentoService cancelamentoService;

    private Sessao sessaoDomain;
    private SessaoEntity sessaoEntityFalsa;
    private Long sessaoId = 1L;

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

        sessaoEntityFalsa = new SessaoEntity();
        sessaoEntityFalsa.setId(sessaoId);
    }

    @Test
    @DisplayName("Deve cancelar uma reserva com sucesso se prazo de cancelamento não expirou")
    void deveCancelarUmaReservaComSucessoSePrazoDeCancelamentoNaoExpirou(){
        String assentoParaCancelar = "A1";

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
        when(sessaoMapper.toEntity(sessaoDomain)).thenReturn(new SessaoEntity());

        cancelamentoService.cancelar(sessaoId, assentoParaCancelar);

        verify(sessaoRepository).save(any(SessaoEntity.class));

        AssentoSessao assentoCancelado = sessaoDomain.getAssentosDaSessao().getFirst();
        assertThat(assentoCancelado.estaDisponivel()).isTrue();
    }

    @Test
    @DisplayName("Deve lançar TempoDeCancelamentoExcedidoException quando o prazo de cancelamento expirar")
    void deveLancarTempoDeCancelamentoExcedidoExceptionQuandoPrazoDeCancelamentoExpirar(){
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));

        LocalDateTime agora = LocalDateTime.now();
        DataHora dataHoraProxima = new DataHora(agora.toLocalDate(), agora.toLocalTime().plusMinutes(4));

        Sessao sessaoQuaseComecando = new Sessao(
                new Filme("Filme legal", 90),
                dataHoraProxima,
                sala
        );
        sessaoQuaseComecando.reservarAssento("A1");

        String assentoParaCancelar = "A1";

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(new SessaoEntity()));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoQuaseComecando);

        assertThrows(TempoDeCancelamentoExcedidoException.class, () -> {
            cancelamentoService.cancelar(sessaoId, assentoParaCancelar);
        });

        verify(sessaoRepository, never()).save(any());
    }



    @Test
    @DisplayName("Deve lançar ReservaInexistenteException ao tentar cancelar um assento não reservado")
    void deveLancarReservaInexistenteExceptionQuandoAssentoNaoEstaReservado() {
        // cria um cenario onde a sessão existe, mas o assento "A2" esta disponível
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));
        sala.getAssentos().add(new Assento("A2"));

        LocalDateTime agora = LocalDateTime.now();
        DataHora dataHoraFutura = new DataHora(agora.toLocalDate(), agora.toLocalTime().plusMinutes(30));

        Sessao sessaoComVagas = new Sessao(
                new Filme("Filme com Vagas", 120),
                dataHoraFutura,
                sala
        );
        // reserva somente A1, deixando A2 livre
        sessaoComVagas.reservarAssento("A1");

        String assentoNaoReservado = "A2";

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(new SessaoEntity()));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoComVagas);

        assertThrows(ReservaInexistenteException.class, () -> {
            cancelamentoService.cancelar(sessaoId, assentoNaoReservado);
        });

        // garantir que a operação foi abortada antes de salvar
        verify(sessaoRepository, never()).save(any());
    }
}