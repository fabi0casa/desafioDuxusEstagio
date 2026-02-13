package br.com.duxusdesafio.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dto.FuncaoResponse;
import br.com.duxusdesafio.dto.TimeDaDataResponse;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.TimeRepository;
import br.com.duxusdesafio.service.ApiService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    private final TimeRepository timeRepository;
    private final ApiService apiService;

    public EstatisticaController(TimeRepository timeRepository,
                                 ApiService apiService) {
        this.timeRepository = timeRepository;
        this.apiService = apiService;
    }

    private List<Time> buscarTodos() {
        return timeRepository.findAll();
    }

    @GetMapping("/time-da-data")
    public TimeDaDataResponse timeDaData(@RequestParam String data) {

        LocalDate dataFormatada = LocalDate.parse(data);

        Time time = apiService.timeDaData(dataFormatada, buscarTodos());

        if (time == null) return null;

        List<String> nomes = time.getComposicaoTime()
                .stream()
                .map(ct -> ct.getIntegrante().getNome())
                .collect(Collectors.toList());

        return new TimeDaDataResponse(time.getData(), nomes);
    }

    @GetMapping("/integrante-mais-usado")
    public Integrante integranteMaisUsado(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return apiService.integranteMaisUsado(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null,
                buscarTodos());
    }

    @GetMapping("/time-mais-comum")
    public List<String> timeMaisComum(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return apiService.integrantesDoTimeMaisComum(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null,
                buscarTodos());
    }

    @GetMapping("/funcao-mais-comum")
    public FuncaoResponse funcaoMaisComum(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        String funcao = apiService.funcaoMaisComum(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null,
                buscarTodos());

        return new FuncaoResponse(funcao);
    }

    @GetMapping("/franquia-mais-famosa")
    public Map<String, String> franquiaMaisFamosa(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        String franquia = apiService.franquiaMaisFamosa(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null,
                buscarTodos());

        return Map.of("franquia", franquia);
    }

    @GetMapping("/contagem-por-franquia")
    public Map<String, Long> contagemPorFranquia(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return apiService.contagemPorFranquia(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null,
                buscarTodos());
    }

    @GetMapping("/contagem-por-funcao")
    public Map<String, Long> contagemPorFuncao(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return apiService.contagemPorFuncao(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null,
                buscarTodos());
    }
}

