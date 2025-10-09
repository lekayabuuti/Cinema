package br.ifsp.demo.service;

import br.ifsp.demo.aplication.service.ReservaIngressoService;
import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.exception.AssentoIndisponivelException;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.infrastructure.entity.IngressoEntity;
import br.ifsp.demo.infrastructure.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.repository.SessaoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
                new Horario(LocalDate.of(2025, 10, 9).plusDays(1), LocalTime.of(20,0)),
                sala
        );

        sessaoEntityFalsa = new SessaoEntity();
        sessaoEntityFalsa.setId(sessaoId);

        //configuração dos mocks
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDomain);
    }

    @Test
    @DisplayName("Deve reservar um ingresso com sucesso, se a sessão e o assento estiverem disponiveis")
    public void deveReservarComSucessoQuandoSessaoEAssentoEstaoDisponiveis(){
        //ARRANGE
        String assentoParaReservar = "A1";
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
        Assertions.assertThrows(AssentoIndisponivelException.class, () ->{
            reservaIngressoService.reservarIngresso(sessaoId, assentoJaReservado);
        });
        verify(sessaoRepository, never()).save(any(SessaoEntity.class));
    }

}