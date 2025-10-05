package br.ifsp.demo.service;
import br.ifsp.demo.domain.model.SessaoCinema;
import br.ifsp.demo.repository.SessaoCinemaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@Tag("TDD")
@ExtendWith(MockitoExtension.class)
public class SessaoCinemaServiceTest {
    @Mock
    SessaoCinemaRepository repository;
    @InjectMocks
    SessaoCinemaService service;

    @Test
    @DisplayName("Visualização de sessões bem-sucedida com data de início menor que a data final")
    void visualizaSessaoCinemaComDataInicialMenorQueDataFinal() {
        LocalDate dataInicial = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = LocalDate.of(2020, 1, 2);

        List<SessaoCinema> mockSessoes = List.of(new SessaoCinema());
        when(repository.findByDataBetween(dataInicial,dataFinal)).thenReturn(mockSessoes);

        List<SessaoCinema> resultado = service.buscarSessoesEntre(dataInicial,dataFinal);
        assertThat(resultado).isNotEmpty();
    }
}
