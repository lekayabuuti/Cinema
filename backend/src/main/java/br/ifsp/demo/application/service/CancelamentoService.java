package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.JpaSessaoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelamentoService {
    private final JpaSessaoRepository jpaSessaoRepository;
    private final SessaoMapper sessaoMapper;

    public CancelamentoService(JpaSessaoRepository jpaSessaoRepository, SessaoMapper sessaoMapper) {
        this.jpaSessaoRepository = jpaSessaoRepository;
        this.sessaoMapper = sessaoMapper;
    }

    @Transactional
    public void cancelar(Long sessaoId, String assentoParaCancelar){
        if (sessaoId == null)
            throw new IllegalArgumentException("ID da sessão não pode ser nulo");

        if (assentoParaCancelar == null || assentoParaCancelar.trim().isEmpty())
            throw new IllegalArgumentException("O código do assento não pode ser nulo ou vazio.");


        //criar sessao entity (puxar a tabela do banco)
        SessaoEntity sessaoEntity = jpaSessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada"));

        Sessao sessaoDomain = sessaoMapper.toDomain(sessaoEntity);

        sessaoDomain.cancelarReserva(assentoParaCancelar, LocalDateTime.now());

        SessaoEntity entityParaSalvar = sessaoMapper.toEntity(sessaoDomain);
        jpaSessaoRepository.save(entityParaSalvar);
    }
}
