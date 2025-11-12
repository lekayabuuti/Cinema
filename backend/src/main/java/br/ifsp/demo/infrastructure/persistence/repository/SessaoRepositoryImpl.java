package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.repository.SessaoRepository;
import br.ifsp.demo.infrastructure.persistence.mapper.SessaoMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class SessaoRepositoryImpl implements SessaoRepository {

    private final JpaSessaoRepository jpaRepository;
    private final SessaoMapper mapper;

    public SessaoRepositoryImpl(JpaSessaoRepository jpaRepository, SessaoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Sessao> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal) {
        return jpaRepository.findByDataBetween(dataInicial, dataFinal)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Sessao> findById(Long sessaoID) {
        return jpaRepository.findById(sessaoID)
                .map(mapper::toDomain);
    }
}