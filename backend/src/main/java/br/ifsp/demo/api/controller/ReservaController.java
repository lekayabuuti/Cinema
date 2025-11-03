package br.ifsp.demo.api.controller;

import br.ifsp.demo.application.service.ReservaIngressoService;
import br.ifsp.demo.infrastructure.persistence.entity.ReservaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {
/*
    private final ReservaIngressoService reservaService;

    @PostMapping
    public ReservaEntity reservar(@RequestBody ReservaEntity reserva) {
        return reservaService.criarReserva(reserva);
    }

    @GetMapping
    public List<ReservaEntity> listarReservas() {
        return reservaService.listarTodas();
    }

 */
}