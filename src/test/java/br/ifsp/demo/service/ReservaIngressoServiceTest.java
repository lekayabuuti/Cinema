package br.ifsp.demo.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.entity.SessaoEntity;
import br.ifsp.demo.repository.SessaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.ArgumentCaptor;
import br.ifsp.demo.entity.IngressoEntity;


@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class ReservaIngressoServiceTest {
    @Mock
    private SessaoRepository sessaoCinemaRepository;

    @InjectMocks
    private ReservaIngressoService reservaIngressoService;

    @Test
    @DisplayName("Deve reservar um ingresso com sucesso, se a sessão e o assento estiverem disponiveis")
    public void deveReservarComSucessoQuandoSessaoEAssentoEstaoDisponiveis(){
        Sala sala01 = new Sala(1);
        Filme filme01 = new Filme("Filme1", 90);
        LocalDate dataDaSessao = LocalDate.of(2025, 10, 6);
        LocalTime horaDaSessao = LocalTime.of(19, 30);
        Horario horario01 = new Horario(dataDaSessao, horaDaSessao);

        Sessao sessaoTeste = new Sessao(filme01, horario01 ,sala01);
        Long sessaoTesteId = 1L;
        List<String> assentosParaReservar = List.of("A1");

        SessaoEntity sessaoEntity = new SessaoEntity();
        sessaoEntity.setId(sessaoTesteId);

        IngressoEntity ingressoDisponivelEntity = new IngressoEntity();
        ingressoDisponivelEntity.setAssento(new Assento("A", sala01));
        ingressoDisponivelEntity.setStatus(Status.DISPONIVEL);
        ingressoDisponivelEntity.setSessao(sessaoEntity);

        sessaoEntity.setIngressos(new ArrayList<>(List.of(ingressoDisponivelEntity)));

        when(sessaoCinemaRepository.findById(sessaoTesteId)).thenReturn(Optional.of(sessaoEntity));

        reservaIngressoService.reservarIngresso(sessaoTesteId, assentosParaReservar);

        ArgumentCaptor<SessaoEntity> captor = ArgumentCaptor.forClass(SessaoEntity.class);
        verify(sessaoCinemaRepository).save(captor.capture());

        SessaoEntity sessaoSalva = captor.getValue();
        IngressoEntity ingressoSalvo = sessaoSalva.getIngressos().get(0);

        assertThat(ingressoSalvo.getStatus()).isEqualTo(Status.RESERVADO);
    }


}