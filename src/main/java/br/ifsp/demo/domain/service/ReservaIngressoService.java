package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.model.Ingresso;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import br.ifsp.demo.infrastructure.persistence.repository.SessaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservaIngressoService {

    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;

    public ReservaIngressoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
    }

    //@Transactional
    public Ingresso reservarIngresso(Long sessaoId, String codigoAssento){
        if (sessaoId==null)
            throw new SessaoInexistenteException("ID de Sessão não pode ser nulo.");

        if (codigoAssento==null || codigoAssento.trim().isEmpty())
            throw new IllegalArgumentException("Código de assento não pode ser nulo ou vazio");

        //1. carregar a entidade do repositório
        SessaoEntity sessaoEntity = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada com o ID: " + sessaoId));

        //2. mapeia para o objeto do dominio
        Sessao sessaoDomain = sessaoMapper.toDomain(sessaoEntity);

        //3. delega a logica de negocio para o metodo de domínio
        AssentoSessao assentoReservado = sessaoDomain.reservarAssento(codigoAssento);

        //4. mapeia o dominio modificado de volta para a entidade e salva
        SessaoEntity entityParaSalvar = sessaoMapper.toEntity(sessaoDomain);
        sessaoRepository.save(entityParaSalvar);

        return new Ingresso(assentoReservado);
    }
}
