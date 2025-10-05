package br.ifsp.demo.service;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.repository.SessaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@Tag("TDD")
@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {
    @Mock
    SessaoRepository repository;
    @InjectMocks
    SessaoService service;

    @Test
    @DisplayName("Visualização de sessões bem-sucedida com data de início menor que a data final")
    void visualizaSessaoCinemaComDataInicialMenorQueDataFinal() {
        LocalDate dataInicial = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = LocalDate.of(2020, 1, 2);

        List<Sessao> mockSessoes = List.of(new Sessao());
        when(repository.buscarEntreDatas(dataInicial,dataFinal)).thenReturn(mockSessoes);

        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial,dataFinal);
        assertThat(resultado).isNotEmpty();
    }
}
