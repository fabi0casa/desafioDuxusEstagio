package br.com.duxusdesafio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dto.TimeRequest;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;
    private final IntegranteRepository integranteRepository;

    public TimeController(TimeRepository timeRepository,
                          IntegranteRepository integranteRepository) {
        this.timeRepository = timeRepository;
        this.integranteRepository = integranteRepository;
    }

    @PostMapping
    public Time cadastrar(@RequestBody TimeRequest request) {

        Time time = new Time();
        time.setData(request.getData());

        List<ComposicaoTime> composicoes = request.getIntegrantesIds()
                .stream()
                .map(id -> {
                    Integrante integrante = integranteRepository.findById(id)
                            .orElseThrow();
                    return new ComposicaoTime(time, integrante);
                })
                .collect(Collectors.toList());

        time.setComposicaoTime(composicoes);

        return timeRepository.save(time);
    }

    @GetMapping
    public List<Time> listar() {
        return timeRepository.findAll();
    }
}

