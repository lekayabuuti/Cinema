package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.AssentoSessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.AssentoSessaoRepository;
import br.ifsp.demo.infrastructure.security.auth.AuthenticationInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscaReservaServiceTest {
    @Mock
    private AssentoSessaoRepository repository;

    @Mock
    private AssentoSessaoMapper mapper;

    @Mock
    private AuthenticationInfoService authService;

    @InjectMocks
    private BuscaReservaService buscaReservaService;

    @Test
    @DisplayName("Deve retornar todas as reservas ativas do usuário")
    void deveRetornarTodasAsReservasAtivasDoUsuario(){
        //primeiro cria o usuário
        UUID usuarioFalsoId = UUID.randomUUID();

        AssentoSessaoEntity entidade1 = new AssentoSessaoEntity();
        AssentoSessaoEntity entidade2 = new AssentoSessaoEntity();
        List<AssentoSessaoEntity> listaDoBanco = List.of(entidade1, entidade2);

        AssentoSessao dominio1 = AssentoSessao.criarNovo(new Assento("A1"), null);
        AssentoSessao dominio2 = AssentoSessao.criarNovo(new Assento("A2"), null);


        when(authService.getAuthenticatedUserId()).thenReturn(usuarioFalsoId);

        when(repository.findByUserIdAndStatus(usuarioFalsoId, Status.RESERVADO))
                .thenReturn(listaDoBanco);

        when(mapper.toDomain(entidade1)).thenReturn(dominio1);
        when(mapper.toDomain(entidade2)).thenReturn(dominio2);

        List<AssentoSessao> resultado = buscaReservaService.buscarMinhasReservas();

        assertThat(resultado)
                .isNotNull() // ...não é nulo
                .hasSize(2) // ...tem o tamanho de 2
                .containsExactlyInAnyOrder(dominio1, dominio2);


    }
}