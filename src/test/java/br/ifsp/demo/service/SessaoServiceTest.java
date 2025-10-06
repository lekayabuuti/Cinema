package br.ifsp.demo.service;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.entity.SessaoEntity;
import br.ifsp.demo.exception.*;
import br.ifsp.demo.mapper.SessaoMapper;
import br.ifsp.demo.repository.DataIndisponivelRepository;
import br.ifsp.demo.repository.SessaoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.*;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@Tag("TDD")
@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {
    @Mock
    SessaoRepository sessaoRepository;
    @Mock
    DataIndisponivelRepository dataIndisponivelRepository;


    @InjectMocks
    SessaoService service;
    @Spy
    SessaoMapper mapper = new SessaoMapper();

    private SessaoEntity novaSessao(String nome, int minutos, LocalDate data, LocalTime hora, int sala) {
        SessaoEntity s = new SessaoEntity();
        s.setFilmeNome(nome);
        s.setFilmeMinutos(minutos);
        s.setHorarioData(data);
        s.setHorarioHora(hora);
        s.setNumeroSala(sala);
        return s;
    }

    @Test
    @DisplayName("Deve retornar sessões quando data inicial for menor que a data final")
    void retornarSessaoCinemaComDataInicialMenorQueDataFinal() {
        LocalDate dataInicial = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = LocalDate.of(2020, 1, 2);
        SessaoEntity entity = novaSessao("Matrix", 136, dataInicial, LocalTime.of(19, 30), 3);

        when(sessaoRepository.findByDataBetween(dataInicial, dataFinal)).thenReturn(List.of(entity));
        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial,dataFinal);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getFilme().nome()).isEqualTo("Matrix");
    }

    @Test
    @DisplayName("Deve retornar sessões quando data inicial for igual a data final")
    void retornarSessaoCinemaComDataInicialIgualDataFinal() {
        LocalDate dataInicial = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = LocalDate.of(2020, 1, 1);
        SessaoEntity entity = novaSessao("Filme Teste", 120, dataInicial, LocalTime.of(20, 0), 1);

        when(sessaoRepository.findByDataBetween(dataInicial, dataFinal)).thenReturn(List.of(entity));
        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial, dataFinal);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getFilme().nome()).isEqualTo("Filme Teste");
        assertThat(resultado.get(0).getHorario().hora()).isEqualTo(LocalTime.of(20, 0));
        assertThat(resultado.get(0).getSala().getNumeroSala()).isEqualTo(1);
    }

    @Test
    @DisplayName("Não deve retornar sessões quando data inicial for 7 dias maior que a data atual")
    void naoRetornarSessaoCinemaComDataInicialSeteDiasMaiorQueDataAtual() {
        LocalDate dataInicial = LocalDate.now().plusDays(8);
        LocalDate dataFinal = dataInicial.plusDays(9);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(DataInvalidaException.class);
    }

    @Test
    @DisplayName("Não deve retornar sessões quando data final for 7 dias maior que a data atual")
    void naoRetornarSessaoCinemaComDataFinalSeteDiasMaiorQueDataAtual(){
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(8);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
        .isInstanceOf(DataInvalidaException.class);
    }

    @Test
    @DisplayName("Deve acionar SessaoIndisponivelException quando data estiver indiponível para sessões")
    void deveLancarExcecaoQuandoDataEstiverIndisponivel(){
        LocalDate dataIndisponivel = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = dataIndisponivel.plusDays(1);

        when(dataIndisponivelRepository.existsByData(dataIndisponivel)).thenReturn(true);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataIndisponivel, dataFinal))
        .isInstanceOf(SessaoIndisponivelException.class);
    }

    @Test
    @DisplayName("Deve acionar DataPassadaException quando data estiver indiponível para sessões")
    void deveLancarExcecaoQuandoDataForUmaDataPassada(){
        LocalDate dataPassada = LocalDate.now().minusDays(1);
        LocalDate dataFinal = dataPassada.plusDays(1);


        assertThatThrownBy(() -> service.buscarSessoesEntre(dataPassada, dataFinal))
                .isInstanceOf(DataPassadaException.class);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nao existir nenhuma sessao entre as datas")
    void  retornarListaVaziaSeNaoExistirNenhumaSessao(){
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(1);
        when(sessaoRepository.findByDataBetween(dataInicial, dataFinal)).thenReturn(List.of());
        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial, dataFinal);
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve acionar DataInvalidaException quando data final for antes de data inicial")
    void  deveLancarExcecaoQuandoDataFinalMenorQueDataInicial(){
        LocalDate dataInicial = LocalDate.now().plusDays(2);
        LocalDate dataFinal =   LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(DataInvalidaException.class);
    }

    @Test
    @DisplayName("Deve acionar NullPointerException quando data inicial for null")
    void  deveLancarExcecaoQuandoDataInicialForNull(){
        LocalDate dataInicial = null;
        LocalDate dataFinal =   LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Deve acionar NullPointerException quando data final for null")
    void  deveLancarExcecaoQuandoDataFinalForNull(){
        LocalDate dataInicial = LocalDate.now().plusDays(1);
        LocalDate dataFinal =   null;

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(NullPointerException.class);
    }

}
