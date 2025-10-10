package br.ifsp.demo.application;

import br.ifsp.demo.domain.exception.SessaoIndisponivelException;
import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.exception.SessaoLotadaException;
import br.ifsp.demo.application.service.ReservaIngressoService;
import br.ifsp.demo.domain.exception.AssentoIndisponivelException;
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
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class ReservaIngressoServiceTest {
    @Mock
    private SessaoRepository sessaoRepository; // mock da camada de persistencia

    @Mock
    private SessaoMapper sessaoMapper; //mock do mapper

    @InjectMocks
    private ReservaIngressoService reservaIngressoService; //classe a ser testada

    private Sessao sessaoDomain;
    private Long sessaoId = 1L;
    private SessaoEntity sessaoEntityFalsa;

    @BeforeEach
    void setUp(){
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento( "A1"));
        sala.getAssentos().add(new Assento( "A2"));

        sessaoDomain = new Sessao(new Filme("Filme legal", 90),
                new DataHora(LocalDate.of(2025, 10, 9).plusDays(1), LocalTime.of(20,0)),
                sala
        );

        sessaoEntityFalsa = new SessaoEntity();
        sessaoEntityFalsa.setId(sessaoId);

        //configuração dos mocks
    }

    @Test
    @DisplayName("Deve reservar um ingresso com sucesso, se a sessão e o assento estiverem disponiveis")
    public void deveReservarComSucessoQuandoSessaoEAssentoEstaoDisponiveis(){
        //ARRANGE
        String assentoParaReservar = "A1";
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
        when(sessaoMapper.toEntity(any(Sessao.class))).thenReturn(new SessaoEntity());


        //ACT / WHEN / Quando...
        Ingresso ingressoGerado = reservaIngressoService.reservarIngresso(sessaoId, assentoParaReservar);

        // ASSERT / THEN / Então...
        //verificar se o recibo foi gerado corretamente
        assertThat(ingressoGerado).isNotNull();
        assertThat(ingressoGerado.getCodigoAssento()).isEqualTo(assentoParaReservar);
        assertThat(ingressoGerado.getFilme().nome()).isEqualTo("Filme legal");
        verify(sessaoRepository).save(any(SessaoEntity.class));
    }

    @Test
    @DisplayName("Deve lançar AssentoIndisponivelException quando o assento já estiver reservado")
    public void deveLancarAssentoIndisponivelExceptionQuandoAssentoJaEstiverReservado(){
        String assentoJaReservado = "A1";
        sessaoDomain.reservarAssento(assentoJaReservado);
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);

        Assertions.assertThrows(AssentoIndisponivelException.class, () ->{
            reservaIngressoService.reservarIngresso(sessaoId, assentoJaReservado);
        });
        verify(sessaoRepository, never()).save(any(SessaoEntity.class));
    }

    @Test
    @DisplayName("Deve lançar SessaoIndisponivelException quando tentar reservar um ou mais ingressos em uma sessão encerrada")
    void deveLancarSessaoIndisponivelExceptionQuandoTentarReservarIngressoEmUmaSessaoEncerrada(){
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));

        DataHora dataHoraPassada = new DataHora(
                LocalDate.of(2025, 10, 1),
                LocalTime.of(20, 0)
        );

        Sessao sessaoEncerrada = new Sessao(
                new Filme("Shrek", 100),
                dataHoraPassada,
                sala
        );
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoEncerrada);

        String assentoParaReservar = "A1";

        Assertions.assertThrows(SessaoIndisponivelException.class, () -> {
            reservaIngressoService.reservarIngresso(sessaoId, assentoParaReservar);
        });

        verify(sessaoRepository, never()).save(any());

    }

    @Test
    @DisplayName("Deve lançar SessaoLotadaException quando tentar reservar um ou mais ingressos em uma sessão encerrada")
    void deveLancarSessaoLotadaExceptionQuandoTentarReservarIngressoEmUmaSessaoLotada(){
        sessaoDomain.reservarAssento("A1");
        sessaoDomain.reservarAssento("A2");

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);

        Assertions.assertThrows(SessaoLotadaException.class, () -> {
            reservaIngressoService.reservarIngresso(sessaoId, "A1");
        });

        verify(sessaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar SessaoInexistenteException quando tentar reservar um ou mais ingressos em uma sessão inexistente")
    void deveLancarSessaoInexistenteExceptionQuandoTentarReservarIngressoEmUmaSessaoInexistente(){
        Long idInexistente = 999L;
        String qualquerAssento = "A1";

        //simula um "não encontrado" no banco de dados
        when(sessaoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(SessaoInexistenteException.class, ()->{
            reservaIngressoService.reservarIngresso(idInexistente, qualquerAssento);
        });

        verify(sessaoMapper, never()).toDomain(any());
        verify(sessaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando tentar reservar um ou mais ingressos em uma sessão cujo ID é vazio")
    void deveLancarIllegalArgumentExceptionQuandoTentarReservarIngressoEmUmaSessaoDeIdVazio(){
        Long idNulo = null;
        String qualquerAssento = "A1";

        Assertions.assertThrows(SessaoInexistenteException.class, ()->{
            reservaIngressoService.reservarIngresso(idNulo, qualquerAssento);
        });

        verify(sessaoRepository, never()).findById(any());
        verify(sessaoMapper, never()).toDomain(any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @DisplayName("Deve lançar IllegalArgumentException ao tentar reservar com código de assento inválido")
    void deveLancarIllegalArgumentExceptionQuandoCodigoAssentoForInvalido(String assentoInvalido){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reservaIngressoService.reservarIngresso(sessaoId, assentoInvalido);
        });
        verifyNoInteractions(sessaoRepository, sessaoMapper);
    }
}