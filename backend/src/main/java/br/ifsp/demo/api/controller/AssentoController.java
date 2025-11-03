package br.ifsp.demo.api.controller;

import br.ifsp.demo.application.service.AssentoSessaoService;
import br.ifsp.demo.infrastructure.persistence.entity.AssentoSessaoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessoes/{sessaoId}/assentos")
@RequiredArgsConstructor
public class AssentoController {
/*
    private final AssentoSessaoService assentoSessaoService;

    @GetMapping
    public List<AssentoSessaoEntity> listarAssentos(@PathVariable UUID sessaoId) {
        return assentoSessaoService.listarPorSessao(sessaoId);
    }

 */
}