package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.SessaoRepository;
import br.ifsp.demo.infrastructure.security.auth.AuthenticationInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CancelamentoService {
    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;
    private final AuthenticationInfoService authService;

    public CancelamentoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper, AuthenticationInfoService authService) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
        this.authService = authService;
    }

    @Transactional
    public void cancelar(Long sessaoId, String assentoParaCancelar){
        if (sessaoId == null)
            throw new IllegalArgumentException("ID da sessão não pode ser nulo");

        if (assentoParaCancelar == null || assentoParaCancelar.trim().isEmpty())
            throw new IllegalArgumentException("O código do assento não pode ser nulo ou vazio.");


        //pegar dados do contexto
        UUID usuarioLogadoId = authService.getAuthenticatedUserId();
        LocalDateTime agora = LocalDateTime.now();

        //criar sessao entity (puxar a tabela do banco)
        SessaoEntity sessaoEntity = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada"));

        Sessao sessaoDomain = sessaoMapper.toDomain(sessaoEntity);

        sessaoDomain.cancelarReserva(assentoParaCancelar, usuarioLogadoId, LocalDateTime.now());

        SessaoEntity entityParaSalvar = sessaoMapper.toEntity(sessaoDomain);
        sessaoRepository.save(entityParaSalvar);
    }
}
