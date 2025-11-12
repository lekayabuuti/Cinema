package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.exception.SessaoIndisponivelException;
import br.ifsp.demo.infrastructure.persistence.repository.JpaDataIndisponivelRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ValidadorDataDisponivelService {

    private final JpaDataIndisponivelRepository jpaDataIndisponivelRepository;

    public ValidadorDataDisponivelService(JpaDataIndisponivelRepository jpaDataIndisponivelRepository) {
        this.jpaDataIndisponivelRepository = jpaDataIndisponivelRepository;
    }

    public void validar(LocalDate data) {
        if(jpaDataIndisponivelRepository.existsByData(data)) {
            throw new SessaoIndisponivelException("Data " + data + " indisponível para sessões");
        }
    }
}