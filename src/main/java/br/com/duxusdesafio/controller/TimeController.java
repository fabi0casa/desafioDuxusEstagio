package br.com.duxusdesafio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dto.TimeRequest;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public Time cadastrar(@RequestBody TimeRequest request) {
        return timeService.cadastrar(request);
    }

    @GetMapping
    public List<Time> listar() {
        return timeService.listar();
    }
}
