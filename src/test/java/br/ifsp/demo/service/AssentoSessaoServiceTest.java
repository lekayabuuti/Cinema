package br.ifsp.demo.service;

import br.ifsp.demo.aplication.service.AssentoSessaoService;
import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.infrastructure.entity.AssentoSessaoEntity;
import br.ifsp.demo.infrastructure.mapper.AssentoMapper;
import br.ifsp.demo.infrastructure.mapper.AssentoSessaoMapper;
import br.ifsp.demo.infrastructure.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.repository.AssentoSessaoRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@Tag("TDD")
public class AssentoSessaoServiceTest {

    @Mock AssentoSessaoRepository repository;
    @Mock AssentoMapper assentoMapper;
    @Mock SessaoMapper sessaoMapper;

    AssentoSessaoMapper mapper;
    AssentoSessaoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mapper = new AssentoSessaoMapper(assentoMapper, sessaoMapper);
        service = new AssentoSessaoService(repository, mapper);
    }

    @Test
    @DisplayName("Deve retornar todos os assentos da sessão, disponíveis ou não")
    void deveRetornarTodosOsAssentosDaSessao() {
        Long sessaoId = 1L;
        List<AssentoSessaoEntity> entidades = List.of(
                new AssentoSessaoEntity(null, null, Status.DISPONIVEL),
                new AssentoSessaoEntity(null, null, Status.RESERVADO)
        );

        when(repository.findBySessaoId(sessaoId)).thenReturn(entidades);

        List<AssentoSessao> resultado = service.buscarPorSessao(sessaoId);

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(AssentoSessao::getStatus)
                .containsExactlyInAnyOrder(Status.DISPONIVEL, Status.RESERVADO);
    }

    @Test
    @DisplayName("Deve retornar todos os assentos disponíveis da sessão")
    void deveRetornarTodosOsAssentosDisponiveisDaSessao() {
        Long sessaoId = 1L;
        List<AssentoSessaoEntity> entidades = List.of(
                new AssentoSessaoEntity(null, null, Status.DISPONIVEL),
                new AssentoSessaoEntity(null, null, Status.RESERVADO)
        );

        when(repository.findBySessaoId(sessaoId)).thenReturn(entidades);
        List<AssentoSessao> resultado = service.buscarAssentosDisponiveis(sessaoId,Status.DISPONIVEL);

        assertThat(resultado).hasSize(1);
        assertThat(resultado).extracting(AssentoSessao::getStatus)
                .containsOnly(Status.DISPONIVEL);
    }


}