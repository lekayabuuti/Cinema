package br.ifsp.demo.application;

import br.ifsp.demo.domain.exception.*;
import br.ifsp.demo.application.service.ReservaIngressoService;
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
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class ReservaIngressoServiceTest {
    @Mock
    private JpaSessaoRepository jpaSessaoRepository; // mock da camada de persistencia

    @Mock
    private SessaoMapper sessaoMapper; //mock do mapper

    @Mock
    private AuthenticationInfoService authService;

    @InjectMocks
    private ReservaIngressoService reservaIngressoService; //classe a ser testada

    private Sessao sessaoDomain;
    private Long sessaoId = 1L;
    private SessaoEntity sessaoEntityFalsa;
    private UUID dummyUserId = UUID.randomUUID();

    @BeforeEach
    void setUp(){
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento( "A1"));
        sala.getAssentos().add(new Assento( "A2"));

        sessaoDomain = new Sessao(new Filme("Filme legal", 90),
                // Usamos uma data bem no futuro (+100 dias) para garantir que os testes de 'caminho feliz'
                // não se tornem frágeis e quebrem com o passar do tempo.
                //É possível aumentar ainda mais essa data, caso atinja esse tempo e o código quebre.
                new DataHora(LocalDate.of(2025, 10, 9).plusDays(100), LocalTime.of(20,0)),
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
        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
        when(sessaoMapper.toEntity(any(Sessao.class))).thenReturn(new SessaoEntity());
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId); //mockar o authService


        //ACT / WHEN / Quando...
        Ingresso ingressoGerado = reservaIngressoService.reservarIngresso(sessaoId, assentoParaReservar);

        // ASSERT / THEN / Então...
        //verificar se o recibo foi gerado corretamente
        assertThat(ingressoGerado).isNotNull();
        assertThat(ingressoGerado.getCodigoAssento()).isEqualTo(assentoParaReservar);
        assertThat(ingressoGerado.getFilme().nome()).isEqualTo("Filme legal");
        verify(jpaSessaoRepository).save(any(SessaoEntity.class));
    }

    @Test
    @DisplayName("Deve lançar AssentoIndisponivelException quando o assento já estiver reservado")
    public void deveLancarAssentoIndisponivelExceptionQuandoAssentoJaEstiverReservado(){
        String assentoJaReservado = "A1";
        sessaoDomain.reservarAssento(assentoJaReservado, dummyUserId);
        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId);

        Assertions.assertThrows(AssentoIndisponivelException.class, () ->{
            reservaIngressoService.reservarIngresso(sessaoId, assentoJaReservado);
        });
        verify(jpaSessaoRepository, never()).save(any(SessaoEntity.class));
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
        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoEncerrada);
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId);

        String assentoParaReservar = "A1";

        Assertions.assertThrows(SessaoIndisponivelException.class, () -> {
            reservaIngressoService.reservarIngresso(sessaoId, assentoParaReservar);
        });

        verify(jpaSessaoRepository, never()).save(any());

    }

    @Test
    @DisplayName("Deve lançar SessaoLotadaException quando tentar reservar um ou mais ingressos em uma sessão encerrada")
    void deveLancarSessaoLotadaExceptionQuandoTentarReservarIngressoEmUmaSessaoLotada(){
        sessaoDomain.reservarAssento("A1", dummyUserId);
        sessaoDomain.reservarAssento("A2", dummyUserId);

        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId); //mockar o authService

        Assertions.assertThrows(SessaoLotadaException.class, () -> {
            reservaIngressoService.reservarIngresso(sessaoId, "A1");
        });

        verify(jpaSessaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar SessaoInexistenteException quando tentar reservar um ou mais ingressos em uma sessão inexistente")
    void deveLancarSessaoInexistenteExceptionQuandoTentarReservarIngressoEmUmaSessaoInexistente(){
        Long idInexistente = 999L;
        String qualquerAssento = "A1";

        //simula um "não encontrado" no banco de dados
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId);
        when(jpaSessaoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(SessaoInexistenteException.class, ()->{
            reservaIngressoService.reservarIngresso(idInexistente, qualquerAssento);
        });

        verify(sessaoMapper, never()).toDomain(any());
        verify(jpaSessaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando tentar reservar um ou mais ingressos em uma sessão cujo ID é vazio")
    void deveLancarIllegalArgumentExceptionQuandoTentarReservarIngressoEmUmaSessaoDeIdVazio(){
        Long idNulo = null;
        String qualquerAssento = "A1";

        Assertions.assertThrows(SessaoInexistenteException.class, ()->{
            reservaIngressoService.reservarIngresso(idNulo, qualquerAssento);
        });

        verify(jpaSessaoRepository, never()).findById(any());
        verify(sessaoMapper, never()).toDomain(any());
        verify(authService, never()).getAuthenticatedUserId(); //garante que auth não foi chamado
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @DisplayName("Deve lançar IllegalArgumentException ao tentar reservar com código de assento inválido")
    void deveLancarIllegalArgumentExceptionQuandoCodigoAssentoForInvalido(String assentoInvalido){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reservaIngressoService.reservarIngresso(sessaoId, assentoInvalido);
        });
        verifyNoInteractions(jpaSessaoRepository, sessaoMapper, authService);
    }

    @Test
    @DisplayName("Deve lançar AssentoInexistenteException quando o assento não for encontrado na sessão")
    void deveLancarAssentoInexistenteExceptionQuandoAssentoNaoForEncontrado(){
        String assentoInexistente = "Z99";
        when(jpaSessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
        when(authService.getAuthenticatedUserId()).thenReturn(dummyUserId);

        Assertions.assertThrows(AssentoInexistenteException.class, ()->{
           reservaIngressoService.reservarIngresso(sessaoId, assentoInexistente);
        });

        verify(jpaSessaoRepository, never()).save(any());
    }



}