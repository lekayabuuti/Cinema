package br.ifsp.demo.service;

import br.ifsp.demo.domain.exception.SessaoInativaException;
import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import br.ifsp.demo.domain.repository.SessaoRepository;
import br.ifsp.demo.domain.service.AssentoSessaoService;
import br.ifsp.demo.domain.enumerations.Status;

import br.ifsp.demo.infrastructure.service.SessaoServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@Tag("TDD")
@ExtendWith(MockitoExtension.class)
public class AssentoSessaoServiceImplTest {

    @Mock AssentoSessaoRepository assentoSessaoRepository;
    @Mock SessaoRepository sessaoRepository;
    @Mock
    SessaoServiceImpl sessaoServiceImpl;
    @Mock Assento assentoMock;
    @Mock Sessao sessaoMock;
    @Mock Filme filmeMock;
    @Mock Sala salaMock;
    @InjectMocks AssentoSessaoService assentoSessaoService;

    private Sessao sessaoEncerrada(){
        DataHora dataAntiga = new DataHora(
                LocalDate.now(), LocalTime.now().minusMinutes(10)
        );
        return new Sessao(filmeMock,dataAntiga,salaMock);
    }


    //19
    @Test
    @Tag("UnitTest")
    @Tag("Functional")
    @DisplayName("Deve retornar todos os assentos da sessão, disponíveis ou não")
    void retornarTodosOsAssentosDaSessao() {
        Long sessaoId = 1L;
        AssentoSessao disponivel = AssentoSessao.reconstituir(assentoMock, sessaoMock, Status.DISPONIVEL);
        AssentoSessao reservado = AssentoSessao.reconstituir(assentoMock, sessaoMock, Status.RESERVADO);
        List<AssentoSessao> modelosDominios = List.of(disponivel, reservado);


        when(assentoSessaoRepository.findBySessaoId(sessaoId)).thenReturn(modelosDominios);

        List<AssentoSessao> resultado = assentoSessaoService.buscarPorSessao(sessaoId);

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(AssentoSessao::getStatus)
                .containsExactlyInAnyOrder(Status.DISPONIVEL, Status.RESERVADO);
    }


    //20
    @Test
    @Tag("UnitTest")
    @Tag("Functional")
    @DisplayName("Deve retornar todos os assentos disponíveis da sessão")
    void deveRetornarTodosOsAssentosDisponiveisDaSessao() {
        AssentoSessao disponivel = AssentoSessao.reconstituir(assentoMock, sessaoMock, Status.DISPONIVEL);
        List<AssentoSessao> modelosDominios = List.of(disponivel);

        when(assentoSessaoRepository.findByAssentoStatus(Status.DISPONIVEL)).thenReturn(modelosDominios);

        List<AssentoSessao> resultado = assentoSessaoService.buscarAssentosPorStatus(Status.DISPONIVEL);

        assertThat(resultado).hasSize(1);
        assertThat(resultado).extracting(AssentoSessao::getStatus)
                .containsOnly(Status.DISPONIVEL);
    }

    //24
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Deve lancar SessaoInativaException ao pesquisar assento de sessao passada")
    void deveLancarExceptionAoPesquisarAssentoSessaoPassada() {
        Long sessaoID = 1L;
        when(sessaoServiceImpl.buscarSessaoPorId(sessaoID))
                .thenThrow(new SessaoInativaException("Sessao Encerrada para consultas!"));

        assertThrows(SessaoInativaException.class, () -> assentoSessaoService.buscarPorSessao(sessaoID));
    }

    //17
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Deve lancar SessaoInexistenteException ao pesquisar assento de sessao inexistente")
    void deveLancarExceptionAoPesquisarSessaoInexistente() {
        Long sessaoID = 1L;
        when(sessaoServiceImpl.buscarSessaoPorId(sessaoID))
                .thenThrow(new SessaoInexistenteException("Sessão não encontrada."));

        assertThrows(SessaoInexistenteException.class, () -> assentoSessaoService.buscarPorSessao(sessaoID));
    }

    //51
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Retorna lista vazia ao procurar assentos de sessao sem assento Disponiveis")
    void retornaListaVaziaAoProcurarAssentosDisponiveis() {
        List<AssentoSessao> listaVazia = List.of();
        Long sessaoID = 1L;
        when(assentoSessaoService.buscarAssentosPorStatus(Status.DISPONIVEL)).thenReturn(listaVazia);

        List<AssentoSessao> resultado = assentoSessaoService.buscarAssentosPorStatus(Status.DISPONIVEL);
        assertThat(resultado).isEmpty();
    }

}