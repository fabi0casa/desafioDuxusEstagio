package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar a função mais comum nos times dentro do período
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        // TODO Implementar método seguindo as instruções!
        return null;
    }


    /**
     * Vai retornar o número (quantidade) de Franquias dentro do período
     */
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

}
