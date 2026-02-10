package com.sonda.gestaoaeronaves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaStatisticsDTO {
    private String marca;
    private Long quantidade;
}
