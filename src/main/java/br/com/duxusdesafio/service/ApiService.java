package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 *
 * OBS ao candidato: PREFERENCIALMENTE, NÃO ALTERE AS ASSINATURAS DOS MÉTODOS!
 * Trabalhe com a proposta pura.
 *
 * @author carlosau
 */
@Service
public class ApiService {

    //Vai retornar um Time, com a composição do time daquela data
    public Time timeDaData(LocalDate data, List<Time> todosOsTimes){
        if (data == null || todosOsTimes == null || todosOsTimes.isEmpty()) {
                return null;
            }

            return todosOsTimes.stream()
                    .filter(time -> data.equals(time.getData()))
                    .findFirst()
                    .orElse(null);
    }

    /**
     * Vai retornar o integrante que estiver presente na maior quantidade de times
     * dentro do período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            return null;
        }

        return todosOsTimes.stream()
            // filtra por período
            .filter(time -> {
                if (time.getData() == null) return false;

                boolean depoisOuIgualInicio = dataInicial == null || !time.getData().isBefore(dataInicial);
                boolean antesOuIgualFim = dataFinal == null || !time.getData().isAfter(dataFinal);

                return depoisOuIgualInicio && antesOuIgualFim;
            })

            // pega todas as composições
            .flatMap(time -> time.getComposicaoTime().stream())

            // extrai integrante
            .map(ComposicaoTime::getIntegrante)

            // agrupa contando
            .collect(Collectors.groupingBy(i -> i, Collectors.counting()))

            // pega o maior
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time mais comum
     * dentro do período
     */
    public List<String> integrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){

        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            return null;
        }

        Map<List<String>, Long> contagem = todosOsTimes.stream()
                .filter(time -> {
                    if (time.getData() == null) return false;

                    boolean depoisOuIgualInicio = dataInicial == null || !time.getData().isBefore(dataInicial);
                    boolean antesOuIgualFim = dataFinal == null || !time.getData().isAfter(dataFinal);

                    return depoisOuIgualInicio && antesOuIgualFim;
                })
                .map(time -> time.getComposicaoTime().stream()
                        .map(ct -> ct.getIntegrante().getNome())
                        .sorted() // importante para garantir igualdade
                        .collect(Collectors.toList()))
                .collect(Collectors.groupingBy(lista -> lista, Collectors.counting()));

        return contagem.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Vai retornar a função mais comum nos times dentro do período
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){

        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            return null;
        }

        return todosOsTimes.stream()
                .filter(time -> {
                    if (time.getData() == null) return false;

                    boolean depoisOuIgualInicio = dataInicial == null || !time.getData().isBefore(dataInicial);
                    boolean antesOuIgualFim = dataFinal == null || !time.getData().isAfter(dataFinal);

                    return depoisOuIgualInicio && antesOuIgualFim;
                })
                .flatMap(time -> time.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFuncao())
                .collect(Collectors.groupingBy(f -> f, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
                return null;
            }

            return todosOsTimes.stream()
                    .filter(time -> {
                        if (time.getData() == null) return false;

                        boolean depoisOuIgualInicio = dataInicial == null || !time.getData().isBefore(dataInicial);
                        boolean antesOuIgualFim = dataFinal == null || !time.getData().isAfter(dataFinal);

                        return depoisOuIgualInicio && antesOuIgualFim;
                    })
                    .flatMap(time -> time.getComposicaoTime().stream())
                    .map(ct -> ct.getIntegrante().getFranquia())
                    .collect(Collectors.groupingBy(f -> f, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
    }


    /**
     * Vai retornar o número (quantidade) de Franquias dentro do período
     */
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        Map<String, Long> resultado = new HashMap<>();

        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            return resultado;
        }

        for (Time time : todosOsTimes) {

            if (time.getData() == null) {
                continue;
            }

            boolean depoisOuIgualInicio =
                    (dataInicial == null) || !time.getData().isBefore(dataInicial);

            boolean antesFim =
                    (dataFinal == null) || time.getData().isBefore(dataFinal);

            if (depoisOuIgualInicio && antesFim) {

                if (time.getComposicaoTime() == null || time.getComposicaoTime().isEmpty()) {
                    continue;
                }

                String franquia = time.getComposicaoTime()
                        .get(0)
                        .getIntegrante()
                        .getFranquia();

                resultado.put(franquia, resultado.getOrDefault(franquia, 0L) + 1);
            }
        }

        return resultado;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial,
                                            LocalDate dataFinal,
                                            List<Time> todosOsTimes) {

        Map<String, Long> resultado = new HashMap<>();
        Set<Integrante> integrantesJaContados = new HashSet<>();

        if (todosOsTimes == null) {
            return resultado;
        }

        for (Time time : todosOsTimes) {

            if (time == null || time.getData() == null) {
                continue;
            }

            if (dataInicial != null && time.getData().isBefore(dataInicial)) {
                continue;
            }

            if (dataFinal != null && !time.getData().isBefore(dataFinal)) {
                continue; // final exclusiva
            }

            if (time.getComposicaoTime() == null) {
                continue;
            }

            for (ComposicaoTime composicao : time.getComposicaoTime()) {

                if (composicao == null || composicao.getIntegrante() == null) {
                    continue;
                }

                Integrante integrante = composicao.getIntegrante();

                // conta cada integrante apenas uma vez no período
                if (integrantesJaContados.add(integrante)) {

                    String funcao = integrante.getFuncao();

                    if (funcao != null) {
                        resultado.put(funcao,
                                resultado.getOrDefault(funcao, 0L) + 1);
                    }
                }
            }
        }

        return resultado;
    }


}
