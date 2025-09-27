package br.ifsp.demo.service;

import br.ifsp.demo.domain.model.Ingresso;
import br.ifsp.demo.domain.model.Reserva;
import br.ifsp.demo.domain.model.SessaoCinema;
import br.ifsp.demo.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaIngressoService {

    private final ReservaRepository reservaRepository;

    public ReservaIngressoService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Reserva reservarIngresso(SessaoCinema sessao, Ingresso ingresso) {
        return reservaRepository.save(new Reserva());
    }
}
