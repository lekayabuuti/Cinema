package br.ifsp.demo.service;

import br.ifsp.demo.domain.exception.SessaoIndisponivelException;
import br.ifsp.demo.infrastructure.service.SessaoServiceImpl;
import br.ifsp.demo.domain.service.ValidadorDataDisponivelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class DataDisponivelServiceTest {
    @Mock
    ValidadorDataDisponivelService validador;


    @InjectMocks
    SessaoServiceImpl service;

    //27
    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Deve acionar SessaoIndisponivelException quando data estiver indiponível para sessões")
    void deveLancarExcecaoQuandoDataEstiverIndisponivel(){
        LocalDate dataIndisponivel = LocalDate.now().plusDays(1);
        LocalDate dataFinal = dataIndisponivel.plusDays(1);

        doThrow(new SessaoIndisponivelException("Data indisponivel")).when(validador).validar(dataIndisponivel);

        assertThatThrownBy(() -> service.buscarSessoesEntre(dataIndisponivel, dataFinal))
                .isInstanceOf(SessaoIndisponivelException.class);
    }
}
