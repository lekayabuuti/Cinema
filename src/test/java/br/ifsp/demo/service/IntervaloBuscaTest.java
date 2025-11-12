package br.ifsp.demo.service;

import br.ifsp.demo.domain.exception.DataInvalidaException;
import br.ifsp.demo.domain.exception.DataPassadaException;
import br.ifsp.demo.infrastructure.service.SessaoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
public class IntervaloBuscaTest {
    @InjectMocks
    SessaoServiceImpl service;
    //36
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Não deve retornar sessões quando data inicial for 7 dias maior que a data atual")
    void naoRetornarSessaoCinemaComDataInicialSeteDiasMaiorQueDataAtual() {
        LocalDate dataInicial = LocalDate.now().plusDays(8);
        LocalDate dataFinal = dataInicial.plusDays(9);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(DataInvalidaException.class);
    }

    //41
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Não deve retornar sessões quando data final for 7 dias maior que a data atual")
    void naoRetornarSessaoCinemaComDataFinalSeteDiasMaiorQueDataAtual(){
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(8);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(DataInvalidaException.class);
    }

    //29
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Deve acionar DataPassadaException quando data estiver indiponível para sessões")
    void deveLancarExcecaoQuandoDataForUmaDataPassada(){
        LocalDate dataPassada = LocalDate.now().minusDays(1);
        LocalDate dataFinal = dataPassada.plusDays(1);


        assertThatThrownBy(() -> service.buscarSessoesEntre(dataPassada, dataFinal))
                .isInstanceOf(DataPassadaException.class);
    }

    //42
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Deve acionar DataInvalidaException quando data final for antes de data inicial")
    void  deveLancarExcecaoQuandoDataFinalMenorQueDataInicial(){
        LocalDate dataInicial = LocalDate.now().plusDays(2);
        LocalDate dataFinal =   LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(DataInvalidaException.class);
    }

    //44
    @Test
    @Tag("UnitTest")
    @DisplayName("Deve acionar NullPointerException quando data inicial for null")
    void  deveLancarExcecaoQuandoDataInicialForNull(){
        LocalDate dataInicial = null;
        LocalDate dataFinal =   LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(NullPointerException.class);
    }

    //45
    @Test
    @Tag("UnitTest")
    @DisplayName("Deve acionar NullPointerException quando data final for null")
    void  deveLancarExcecaoQuandoDataFinalForNull(){
        LocalDate dataInicial = LocalDate.now().plusDays(1);
        LocalDate dataFinal =   null;

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataInicial, dataFinal))
                .isInstanceOf(NullPointerException.class);
    }
}
