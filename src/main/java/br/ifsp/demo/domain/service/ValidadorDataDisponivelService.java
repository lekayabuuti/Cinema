package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.exception.SessaoIndisponivelException;
import br.ifsp.demo.infrastructure.persistence.repository.DataIndisponivelRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ValidadorDataDisponivelService {

    private final DataIndisponivelRepository dataIndisponivelRepository;

    public ValidadorDataDisponivelService(DataIndisponivelRepository dataIndisponivelRepository) {
        this.dataIndisponivelRepository = dataIndisponivelRepository;
    }

    public void validar(LocalDate data) {
        if(dataIndisponivelRepository.existsByData(data)) {
            throw new SessaoIndisponivelException("Data " + data + " indisponível para sessões");
        }
    }
}