package br.com.duxusdesafio.dto;

import java.time.LocalDate;
import java.util.List;

public class TimeRequest {

    private LocalDate data;
    private List<Long> integrantesIds;

    public LocalDate getData() {
        return data;
    }

    public List<Long> getIntegrantesIds() {
        return integrantesIds;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setIntegrantesIds(List<Long> integrantesIds) {
        this.integrantesIds = integrantesIds;
    }
}

