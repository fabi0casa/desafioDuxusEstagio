package br.com.duxusdesafio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;

@Service
public class IntegranteService {

    private final IntegranteRepository repository;

    public IntegranteService(IntegranteRepository repository) {
        this.repository = repository;
    }

    public Integrante cadastrar(Integrante integrante) {
        return repository.save(integrante);
    }

    public List<Integrante> listar() {
        return repository.findAll();
    }
}
