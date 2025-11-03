package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.SessaoInexistenteException;
import br.ifsp.demo.domain.model.AssentoSessao;
import br.ifsp.demo.domain.model.Ingresso;
import br.ifsp.demo.domain.model.Reserva;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.domain.repository.ReservaRepository;
import br.ifsp.demo.domain.repository.SessaoRepository;
import br.ifsp.demo.infrastructure.security.auth.AuthenticationInfoService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {

    private final SessaoRepository sessaoRepository;
    private final ReservaRepository reservaRepository;
    private final AuthenticationInfoService authService;

    public ReservaService(SessaoRepository sessaoRepository,
                          ReservaRepository reservaRepository,
                          AuthenticationInfoService authService) {
        this.sessaoRepository = sessaoRepository;
        this.reservaRepository = reservaRepository;
        this.authService = authService;
    }

    public Ingresso reservarIngresso(Long sessaoId, String codigoAssento) {
        UUID usuarioLogadoId = authService.getAuthenticatedUserId();

        if (sessaoId == null)
            throw new SessaoInexistenteException("ID de Sessão não pode ser nulo.");

        if (codigoAssento == null || codigoAssento.trim().isEmpty())
            throw new IllegalArgumentException("Código de assento não pode ser nulo ou vazio");

        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada com o ID: " + sessaoId));

        AssentoSessao assentoReservado = sessao.reservarAssento(codigoAssento, usuarioLogadoId);

        Reserva reserva = new Reserva();
        reserva.setSessao(sessao);
        reserva.setIngressos(Collections.singletonList(new Ingresso(assentoReservado)));

        reservaRepository.salvar(reserva);

        return new Ingresso(assentoReservado);
    }

    public List<Reserva> buscarReservasPorSessaoEUsuario(Long sessaoId, UUID usuarioId) {
        if (sessaoId == null )
            throw new NullPointerException("ID da sessão deve ser um número positivo e não nulo");

        if (sessaoId <= 0)
            throw new IllegalArgumentException("ID do usuário não pode negativo ou vazio");
        if (usuarioId == null)
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");

        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoInexistenteException("Sessão não encontrada com o ID: " + sessaoId));

        return reservaRepository.buscarPorSessaoEUsuario(sessaoId, usuarioId);
    }

    public List<Reserva> buscarReservasPorSessoesDoUsuario(List<Long> sessoesId) {
        UUID usuarioId = authService.getAuthenticatedUserId();

        if (sessoesId == null || sessoesId.isEmpty())
            throw new IllegalArgumentException("Lista de IDs de sessão não pode ser nula ou vazia");

        return sessoesId.stream()
                .map(sessaoId -> {
                    boolean existe = sessaoRepository.findById(sessaoId).isPresent();
                    if (!existe)
                        throw new SessaoInexistenteException("Sessão não encontrada com o ID: " + sessaoId);
                    return reservaRepository.buscarPorSessaoEUsuario(sessaoId, usuarioId);
                })
                .flatMap(List::stream)
                .toList();
    }

    public List<Reserva> buscarTodasReservasDoUsuario() {
        UUID usuarioId = authService.getAuthenticatedUserId();
        return reservaRepository.buscarPorUsuario(usuarioId);
    }



}