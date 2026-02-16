package br.com.duxusdesafio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.duxusdesafio.dto.TimeRequest;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;
    private final IntegranteRepository integranteRepository;

    public TimeService(TimeRepository timeRepository,
                       IntegranteRepository integranteRepository) {
        this.timeRepository = timeRepository;
        this.integranteRepository = integranteRepository;
    }

    public Time cadastrar(TimeRequest request) {

        Time time = new Time();
        time.setData(request.getData());

        List<ComposicaoTime> composicoes = request.getIntegrantesIds()
                .stream()
                .map(id -> {
                    Integrante integrante = integranteRepository.findById(id)
                            .orElseThrow(() -> 
                                new RuntimeException("Integrante não encontrado: " + id));
                    return new ComposicaoTime(time, integrante);
                })
                .collect(Collectors.toList());

        time.setComposicaoTime(composicoes);

        return timeRepository.save(time);
    }

    public List<Time> listar() {
        return timeRepository.findAll();
    }
}
