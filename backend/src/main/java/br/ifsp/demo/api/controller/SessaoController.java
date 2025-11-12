package br.ifsp.demo.api.controller;

import br.ifsp.demo.application.service.SessaoService;
import br.ifsp.demo.domain.model.Sessao;
import br.ifsp.demo.infrastructure.persistence.entity.SessaoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sessoes")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService sessaoService;


    @GetMapping("/periodo")
    public List<Sessao> buscarPorPeriodo(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return sessaoService.buscarPorPeriodo(inicio, fim);
    }

    @GetMapping("/{id}")
    public Sessao buscarPorId(@PathVariable Long id) {
        return sessaoService.buscarPorId(id);
    }


}