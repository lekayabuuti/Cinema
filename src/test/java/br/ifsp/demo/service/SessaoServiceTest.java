package br.ifsp.demo.service;
import br.ifsp.demo.domain.service.SessaoService;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.domain.service.ValidadorDataDisponivelService;
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
    ValidadorDataDisponivelService validador;

    @InjectMocks
    SessaoService service;

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





}
