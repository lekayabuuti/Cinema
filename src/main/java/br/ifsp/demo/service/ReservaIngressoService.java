package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.model.Ingresso;
import br.ifsp.demo.domain.model.Reserva;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.entity.SessaoEntity;
import br.ifsp.demo.mapper.SessaoMapper;
import br.ifsp.demo.repository.ReservaRepository;
import br.ifsp.demo.repository.SessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaIngressoService {

    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;

    public ReservaIngressoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
    }

    @Transactional
    public Ingresso reservarIngresso(Long sessaoId, String codigoAssento) { // recebe só um assento por vez
        // 1 - carrega a SessaoEntity
        SessaoEntity sessaoEntity = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com o ID: " + sessaoId));

        // 2- mapeia para o domínio
        Sessao sessaoDomain = SessaoMapper.toDomain(sessaoEntity);

        // 3- chama a lógica de negócio no domínio
        AssentoSessao assentoReservado = sessaoDomain.reservarAssento(codigoAssento);

        // 4 - salva (o JPA detectará as mudanças na transação)
        sessaoRepository.save(sessaoEntity);

        // 5- gera o "recibo" final
        return new Ingresso(assentoReservado);
    }
}
