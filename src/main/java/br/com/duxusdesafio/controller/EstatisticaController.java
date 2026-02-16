package br.com.duxusdesafio.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dto.FuncaoResponse;
import br.com.duxusdesafio.dto.TimeDaDataResponse;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.service.EstatisticaService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping("/time-da-data")
    public TimeDaDataResponse timeDaData(@RequestParam String data) {
        return estatisticaService.timeDaData(LocalDate.parse(data));
    }

    @GetMapping("/integrante-mais-usado")
    public Integrante integranteMaisUsado(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return estatisticaService.integranteMaisUsado(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null);
    }

    @GetMapping("/time-mais-comum")
    public List<String> timeMaisComum(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return estatisticaService.timeMaisComum(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null);
    }

    @GetMapping("/funcao-mais-comum")
    public FuncaoResponse funcaoMaisComum(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return new FuncaoResponse(
                estatisticaService.funcaoMaisComum(
                        dataInicial != null ? LocalDate.parse(dataInicial) : null,
                        dataFinal != null ? LocalDate.parse(dataFinal) : null));
    }

    @GetMapping("/franquia-mais-famosa")
    public Map<String, String> franquiaMaisFamosa(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return Map.of("franquia",
                estatisticaService.franquiaMaisFamosa(
                        dataInicial != null ? LocalDate.parse(dataInicial) : null,
                        dataFinal != null ? LocalDate.parse(dataFinal) : null));
    }

    @GetMapping("/contagem-por-franquia")
    public Map<String, Long> contagemPorFranquia(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return estatisticaService.contagemPorFranquia(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null);
    }

    @GetMapping("/contagem-por-funcao")
    public Map<String, Long> contagemPorFuncao(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {

        return estatisticaService.contagemPorFuncao(
                dataInicial != null ? LocalDate.parse(dataInicial) : null,
                dataFinal != null ? LocalDate.parse(dataFinal) : null);
    }
}