package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import br.ifsp.demo.domain.service.AssentoSessaoService;
import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@Tag("TDD")
@ExtendWith(MockitoExtension.class)
public class AssentoSessaoServiceTest {

    @Mock
    AssentoSessaoRepository repository;
    @InjectMocks
    AssentoSessaoService service;
    @Mock
    Assento assentoMock;
    @Mock
    Sessao sessaoMock;


    //19
    @Test
    @Tag("UnitTest")
    @Tag("Functional")
    @DisplayName("Deve retornar todos os assentos da sessão, disponíveis ou não")
    void deveRetornarTodosOsAssentosDaSessao() {
        Long sessaoId = 1L;
        AssentoSessao disponivel = AssentoSessao.reconstituir(assentoMock, sessaoMock, Status.DISPONIVEL);
        AssentoSessao reservado = AssentoSessao.reconstituir(assentoMock, sessaoMock, Status.RESERVADO);
        List<AssentoSessao> modelosDominios = List.of(disponivel, reservado);


        when(repository.findBySessaoId(sessaoId)).thenReturn(modelosDominios);

        List<AssentoSessao> resultado = service.buscarPorSessao(sessaoId);

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

        when(repository.findByAssentoStatus(Status.DISPONIVEL)).thenReturn(modelosDominios);

        List<AssentoSessao> resultado = service.buscarAssentosPorStatus(Status.DISPONIVEL);

        assertThat(resultado).hasSize(1);
        assertThat(resultado).extracting(AssentoSessao::getStatus)
                .containsOnly(Status.DISPONIVEL);
    }

}