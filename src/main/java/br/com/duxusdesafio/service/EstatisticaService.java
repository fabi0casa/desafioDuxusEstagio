package br.com.duxusdesafio.service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.duxusdesafio.dto.TimeDaDataResponse;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.TimeRepository;

@Service
public class EstatisticaService {

    private final TimeRepository timeRepository;

    public EstatisticaService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    private List<Time> buscarTodos() {
        return timeRepository.findAll();
    }

    private List<Time> filtrarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return buscarTodos().stream()
                .filter(t -> {
                    if (inicio != null && t.getData().isBefore(inicio)) return false;
                    if (fim != null && t.getData().isAfter(fim)) return false;
                    return true;
                })
                .collect(Collectors.toList());
    }

    public TimeDaDataResponse timeDaData(LocalDate data) {

        Time time = buscarTodos().stream()
                .filter(t -> t.getData().equals(data))
                .findFirst()
                .orElse(null);

        if (time == null) return null;

        List<String> nomes = time.getComposicaoTime()
                .stream()
                .map(ct -> ct.getIntegrante().getNome())
                .collect(Collectors.toList());

        return new TimeDaDataResponse(time.getData(), nomes);
    }

    public Integrante integranteMaisUsado(LocalDate inicio, LocalDate fim) {

        return filtrarPorPeriodo(inicio, fim).stream()
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<String> timeMaisComum(LocalDate inicio, LocalDate fim) {

        return filtrarPorPeriodo(inicio, fim).stream()
                .collect(Collectors.groupingBy(
                        t -> t.getComposicaoTime()
                                .stream()
                                .map(ct -> ct.getIntegrante().getNome())
                                .sorted()
                                .collect(Collectors.toList()),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Collections.emptyList());
    }

    public String funcaoMaisComum(LocalDate inicio, LocalDate fim) {

        return filtrarPorPeriodo(inicio, fim).stream()
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFuncao())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public String franquiaMaisFamosa(LocalDate inicio, LocalDate fim) {

        return filtrarPorPeriodo(inicio, fim).stream()
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFranquia())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Map<String, Long> contagemPorFranquia(LocalDate inicio, LocalDate fim) {

        return filtrarPorPeriodo(inicio, fim).stream()
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFranquia())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Map<String, Long> contagemPorFuncao(LocalDate inicio, LocalDate fim) {

        return filtrarPorPeriodo(inicio, fim).stream()
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFuncao())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
