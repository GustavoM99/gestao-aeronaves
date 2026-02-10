package com.sonda.gestaoaeronaves.dto;

import com.sonda.gestaoaeronaves.model.Aeronave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AeronaveResponseDTO {

    private Long id;
    private String nome;
    private String marca;
    private Integer ano;
    private String descricao;
    private Boolean vendido;
    private LocalDateTime created;
    private LocalDateTime updated;

    public static AeronaveResponseDTO fromEntity(Aeronave aeronave) {
        return new AeronaveResponseDTO(
            aeronave.getId(),
            aeronave.getNome(),
            aeronave.getMarca(),
            aeronave.getAno(),
            aeronave.getDescricao(),
            aeronave.getVendido(),
            aeronave.getCreated(),
            aeronave.getUpdated()
        );
    }
}
