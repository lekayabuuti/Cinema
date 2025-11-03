package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.enumerations.Status;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.AssentoSessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.AssentoSessaoRepository;
import br.ifsp.demo.infrastructure.security.auth.AuthenticationInfoService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BuscaReservaService {

    private final AssentoSessaoRepository repository;
    private final AssentoSessaoMapper mapper;
    private final AuthenticationInfoService authService;

    public BuscaReservaService(AssentoSessaoRepository repository, AssentoSessaoMapper mapper, AuthenticationInfoService authService) {
        this.repository = repository;
        this.mapper = mapper;
        this.authService = authService;
    }


    public List<AssentoSessao> buscarMinhasReservas(){
        UUID usuarioLogadoId = authService.getAuthenticatedUserId();

        List<AssentoSessaoEntity> entidadesDoBanco = repository.findByUserIdAndStatus(usuarioLogadoId, Status.RESERVADO);

        return entidadesDoBanco.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
