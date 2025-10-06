package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.repository.SessaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class ReservaIngressoServiceTest {
    @Mock
    private SessaoRepository sessaoCinemaRepository;

    @InjectMocks
    private ReservaIngressoService reservaIngressoService;

    @Test
    @DisplayName("Deve reservar um ingresso com sucesso a sessão e o assento estão disponiveis")
    public void deveReservarComSucessoQuandoSessaoEAssentoEstaoDisponiveis(){
        Sala sala01 = new Sala(1);
        Filme filme01 = new Filme("Filme1", 90);
        LocalDate dataDaSessao = LocalDate.of(2025, 10, 6);
        LocalTime horaDaSessao = LocalTime.of(19, 30);
        Horario horario01 = new Horario(dataDaSessao, horaDaSessao);

        Sessao sessaoTeste = new Sessao(filme01, horario01 ,sala01);
        Long sessaoTesteId = 1L;
        sessaoTeste.setId(sessaoTesteId);

        when(sessaoCinemaRepository.findById(sessaoTesteId)).thenReturn(Optional.of(sessaoTeste));
    }


}