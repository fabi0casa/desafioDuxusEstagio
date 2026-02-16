package br.com.duxusdesafio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.service.IntegranteService;

@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

    private final IntegranteService integranteService;

    public IntegranteController(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    @PostMapping
    public Integrante cadastrar(@RequestBody Integrante integrante) {
        return integranteService.cadastrar(integrante);
    }

    @GetMapping
    public List<Integrante> listar() {
        return integranteService.listar();
    }
}