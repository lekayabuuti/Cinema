package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.SessaoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelamentoService {
    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;

    public CancelamentoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
    }

    @Transactional
    public void cancelar(Long sessaoId, String assentoParaCancelar){
        //1 criar sessao entity (puxar a tabela do banco)
        SessaoEntity sessaoEntity = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada"));

        Sessao sessaoDomain = sessaoMapper.toDomain(sessaoEntity);

        sessaoDomain.cancelarReserva(assentoParaCancelar, LocalDateTime.now());

        SessaoEntity entityParaSalvar = sessaoMapper.toEntity(sessaoDomain);
        sessaoRepository.save(entityParaSalvar);
    }
}
