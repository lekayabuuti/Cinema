package br.ifsp.demo.application;

import br.ifsp.demo.application.service.AssentoSessaoService;
import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.Assento;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.repository.AssentoSessaoRepository;
import br.ifsp.demo.domain.service.SessaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class AssentoSessaoServiceTest {
    @Mock
    AssentoSessaoRepository assentoSessaoRepository;
    @Mock
    SessaoService sessaoService;

    @Mock
    Assento assentoMock;
    @Mock
    Sessao sessaoMock;

    @InjectMocks
    AssentoSessaoService assentoSessaoService;

    // #52
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Deve lançar IllegalArgumentException quando o ID da sessão for nulo (defesa #52)")
    void deveLancarExceptionQuandoIdSessaoForNulo() {
        assertThrows(IllegalArgumentException.class, () -> assentoSessaoService.buscarPorSessao(null, false));
    }

    // #17
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Deve lançar SessaoInexistenteException ao pesquisar assento de sessão inexistente (#17)")
    void deveLancarExceptionAoPesquisarSessaoInexistente() {
        Long sessaoID = 1L;

        when(sessaoService.buscarSessaoPorId(sessaoID))
                .thenThrow(new SessaoInexistenteException("Sessão não encontrada."));

        assertThrows(SessaoInexistenteException.class, () -> assentoSessaoService.buscarPorSessao(sessaoID,true));
    }

}
