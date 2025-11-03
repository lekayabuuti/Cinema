package br.ifsp.demo.domain.service;

import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.model.Ingresso;
import br.ifsp.demo.domain.model.Reserva;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.repository.ReservaRepository;
import br.ifsp.demo.domain.repository.SessaoRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.UUID;

@Service
public class ReservaService {

    private final SessaoRepository sessaoRepository;
    private final ReservaRepository reservaRepository;

    public ReservaService(SessaoRepository sessaoRepository, ReservaRepository reservaRepository) {
        this.sessaoRepository = sessaoRepository;
        this.reservaRepository = reservaRepository;
    }

    public Ingresso reservarIngresso(Long sessaoId, String codigoAssento, UUID usuarioId) {
        if (sessaoId == null) {
            throw new SessaoInexistenteException("ID de Sessão não pode ser nulo.");
        }

        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada com o ID: " + sessaoId));

        AssentoSessao assentoReservado = sessao.reservarAssento(codigoAssento, usuarioId);

        Reserva reserva = new Reserva();
        reserva.setSessao(sessao);
        reserva.setIngressos(Collections.singletonList(new Ingresso(assentoReservado)));

        reservaRepository.salvar(reserva);

        return new Ingresso(assentoReservado);
    }
}