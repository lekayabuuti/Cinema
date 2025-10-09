package br.ifsp.demo.service;
import br.ifsp.demo.domain.service.SessaoService;
import br.ifsp.demo.domain.exception.DataInvalidaException;
import br.ifsp.demo.domain.exception.DataPassadaException;
import br.ifsp.demo.domain.exception.SessaoIndisponivelException;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.DataIndisponivelRepository;
import br.ifsp.demo.domain.repository.SessaoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.*;
import java.util.List;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
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

    private Sessao novaSessao(String nome, int minutos, LocalDate data, LocalTime hora, Integer numero) {
        Filme filme = new Filme(nome,minutos);
        Sala sala = new Sala(numero);
        DataHora dataHora= new DataHora(data,hora);

        return new Sessao(filme,dataHora,sala);
    }

    //#47
    @Test
    @DisplayName("Deve retornar sessões quando data inicial for menor que a data final")
    void retornarSessaoCinemaComDataInicialMenorQueDataFinal() {
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = LocalDate.now().plusDays(1);
        Sessao sessao =  novaSessao("Matrix", 136, dataInicial, LocalTime.of(19, 30), 3);

        when(sessaoRepository.findByDataBetween(dataInicial, dataFinal)).thenReturn(List.of(sessao));
        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial,dataFinal);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
    }

    //46
    @Test
    @DisplayName("Deve retornar sessões quando data inicial for igual a data final")
    void retornarSessaoCinemaComDataInicialIgualDataFinal() {
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = LocalDate.now().plusDays(1);
        Sessao sessao = novaSessao("Filme Teste", 120, dataInicial, LocalTime.of(20, 0), 1);

        when(sessaoRepository.findByDataBetween(dataInicial, dataFinal)).thenReturn(List.of(sessao));
        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial, dataFinal);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getFilme().nome()).isEqualTo("Filme Teste");
        assertThat(resultado.get(0).getDataHora().hora()).isEqualTo(LocalTime.of(20, 0));
        assertThat(resultado.get(0).getSala().getNumeroSala()).isEqualTo(1);
    }

    //36
    @Test
    @DisplayName("Não deve retornar sessões quando data inicial for 7 dias maior que a data atual")
    void naoRetornarSessaoCinemaComDataInicialSeteDiasMaiorQueDataAtual() {
        LocalDate dataInicial = LocalDate.now().plusDays(8);
        LocalDate dataFinal = dataInicial.plusDays(9);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(DataInvalidaException.class);
    }

    //41
    @Test
    @DisplayName("Não deve retornar sessões quando data final for 7 dias maior que a data atual")
    void naoRetornarSessaoCinemaComDataFinalSeteDiasMaiorQueDataAtual(){
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(8);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
        .isInstanceOf(DataInvalidaException.class);
    }

    //27
    @Test
    @DisplayName("Deve acionar SessaoIndisponivelException quando data estiver indiponível para sessões")
    void deveLancarExcecaoQuandoDataEstiverIndisponivel(){
        LocalDate dataIndisponivel = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = dataIndisponivel.plusDays(1);

        when(dataIndisponivelRepository.existsByData(dataIndisponivel)).thenReturn(true);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataIndisponivel, dataFinal))
        .isInstanceOf(SessaoIndisponivelException.class);
    }

    //29
    @Test
    @DisplayName("Deve acionar DataPassadaException quando data estiver indiponível para sessões")
    void deveLancarExcecaoQuandoDataForUmaDataPassada(){
        LocalDate dataPassada = LocalDate.now().minusDays(1);
        LocalDate dataFinal = dataPassada.plusDays(1);


        assertThatThrownBy(() -> service.buscarSessoesEntre(dataPassada, dataFinal))
                .isInstanceOf(DataPassadaException.class);
    }

    //35
    @Test
    @DisplayName("Deve retornar lista vazia quando nao existir nenhuma sessao entre as datas")
    void  retornarListaVaziaSeNaoExistirNenhumaSessao(){
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(1);
        when(sessaoRepository.findByDataBetween(dataInicial, dataFinal)).thenReturn(List.of());
        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial, dataFinal);
        assertThat(resultado).isEmpty();
    }

    //42
    @Test
    @DisplayName("Deve acionar DataInvalidaException quando data final for antes de data inicial")
    void  deveLancarExcecaoQuandoDataFinalMenorQueDataInicial(){
        LocalDate dataInicial = LocalDate.now().plusDays(2);
        LocalDate dataFinal =   LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(DataInvalidaException.class);
    }

    //44
    @Test
    @DisplayName("Deve acionar NullPointerException quando data inicial for null")
    void  deveLancarExcecaoQuandoDataInicialForNull(){
        LocalDate dataInicial = null;
        LocalDate dataFinal =   LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(NullPointerException.class);
    }

    //45
    @Test
    @DisplayName("Deve acionar NullPointerException quando data final for null")
    void  deveLancarExcecaoQuandoDataFinalForNull(){
        LocalDate dataInicial = LocalDate.now().plusDays(1);
        LocalDate dataFinal =   null;

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(NullPointerException.class);
    }

}
