package br.ifsp.demo.api.controller;

import br.ifsp.demo.application.service._ReservaServiceTEMPORARIO.ReservaService;
import br.ifsp.demo.domain.model.Ingresso;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public Ingresso reservarIngresso(
            @RequestParam("sessaoId") Long sessaoId,
            @RequestParam("codigoAssento") String codigoAssento) {/// ////////
        return reservaService.reservarIngresso(sessaoId, codigoAssento);
    }


}