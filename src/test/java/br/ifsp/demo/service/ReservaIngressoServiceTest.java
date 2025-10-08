package br.ifsp.demo.service;

import br.ifsp.demo.aplication.service.ReservaIngressoService;
import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.infrastructure.entity.IngressoEntity;
import br.ifsp.demo.infrastructure.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.repository.SessaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("TDD")
class ReservaIngressoServiceTest {
    @Mock
    private SessaoRepository sessaoRepository; // mock da camada de persistencia

    @Mock
    private SessaoMapper sessaoMapper; //mock do mapper

    @InjectMocks
    private ReservaIngressoService reservaIngressoService; //classe a ser testada

    @Test
    @DisplayName("Deve reservar um ingresso com sucesso, se a sessão e o assento estiverem disponiveis")
    public void deveReservarComSucessoQuandoSessaoEAssentoEstaoDisponiveis(){

        //ARRANGE / GIVEN / dado que...
        //1 - criando os OBJETOS DE DOMÍNIO que representam o estado inicial
        Sala sala = new Sala(1);
        sala.getAssentos().add(new Assento("A1"));
        sala.getAssentos().add(new Assento("A2"));

        Sessao sessaoDeDominio = new Sessao(new Filme("Filme legal", 90),
                new Horario(LocalDate.now().plusDays(1), LocalTime.of(20,0)),
                sala
        );
        Long sessaoId = 1L;
        String assentoParaReservar = "A1";

        //2- Criando uma entidade falsa que o repositorio "retornará do banco de dados"
        //nao é necessario ter todos os dados, pois só o ID é suficiente para o mock.
        SessaoEntity sessaoEntityFalsa = new SessaoEntity();
        sessaoEntityFalsa.setId(sessaoId);

        //3 - Configurando os mocks.
        // quando o repositorio procurar pelo Id, retorna a entidade falsa
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoEntityFalsa));
        // qnd o mapper for converter a entidade falsa, retornará objeto de dominio compleeto
        when(sessaoMapper.toDomain(any(SessaoEntity.class))).thenReturn(sessaoDeDominio);


        // ACT / WHEN / Quando...

        // O serviço retorna o "recibo" da operação
        Ingresso ingressoGerado = reservaIngressoService.reservarIngresso(sessaoId, assentoParaReservar);

        // ASSERT / THEN / Então...
        //1 - verificar se o recibo foi gerado corretamente
        assertThat(ingressoGerado).isNotNull();
        assertThat(ingressoGerado.getCodigoAssento()).isEqualTo(assentoParaReservar);
        assertThat(ingressoGerado.getFilme().nome()).isEqualTo("Filme legal");

        //verificar se a sessao foi salva
        verify(sessaoRepository).save(any(SessaoEntity.class));
    }


}