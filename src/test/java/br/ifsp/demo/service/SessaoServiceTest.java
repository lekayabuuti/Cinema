package br.ifsp.demo.service;
import br.ifsp.demo.domain.model.*;
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
    SessaoRepository repository;
    @InjectMocks
    SessaoService service;

    @Test
    @DisplayName("Deve retornar sessões quando data inicial for menor que a data final")
    void visualizaSessaoCinemaComDataInicialMenorQueDataFinal() {
        LocalDate dataInicial = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = LocalDate.of(2020, 1, 2);
        Filme filme = new Filme("Filme Teste", 120);
        Horario horario = new Horario(LocalDate.of(2020, 1, 1), LocalTime.of(20, 0));
        Sala sala = new Sala(1);

        List<Sessao> mockSessoes = List.of(new Sessao(filme, horario, sala));
        when(repository.buscarEntreDatas(dataInicial,dataFinal)).thenReturn(mockSessoes);

        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial,dataFinal);
        assertThat(resultado).isNotEmpty();
    }

    @Test
    @DisplayName("Deve retornar sessões quando data inicial for igual a data final")
    void visualizaSessaoCinemaComDataInicialIgualDataFinal(){
        LocalDate dataInicial = LocalDate.of(2020, 1, 1);
        LocalDate dataFinal = LocalDate.of(2020, 1, 1);
        Filme filme = new Filme("Filme Teste", 120);
        Horario horario = new Horario(LocalDate.of(2020, 1, 1), LocalTime.of(20, 0));
        Sala sala = new Sala(1);

        List<Sessao> mockSessao = List.of( new Sessao(filme,horario,sala));
        when(repository.buscarEntreDatas(dataInicial,dataFinal)).thenReturn(mockSessao);

        List<Sessao> resultado = service.buscarSessoesEntre(dataInicial,dataFinal);
        assertThat(resultado).isNotEmpty();
    }




}
