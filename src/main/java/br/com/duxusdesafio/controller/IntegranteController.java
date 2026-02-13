package br.com.duxusdesafio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;

@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

    private final IntegranteRepository repository;

    public IntegranteController(IntegranteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Integrante cadastrar(@RequestBody Integrante integrante) {
        return repository.save(integrante);
    }

    @GetMapping
    public List<Integrante> listar() {
        return repository.findAll();
    }
}

