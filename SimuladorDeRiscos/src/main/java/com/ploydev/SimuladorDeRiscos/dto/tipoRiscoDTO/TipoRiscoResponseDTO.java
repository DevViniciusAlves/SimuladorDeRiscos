package com.ploydev.SimuladorDeRiscos.dto.tipoRiscoDTO;

import com.ploydev.SimuladorDeRiscos.entity.NivelRiscoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoRiscoResponseDTO {

    private UUID id;
    private NivelRiscoEnum nivelRisco;
    private String descricao;
    private Integer porcentagemMinima;
    private Integer porcentagemMaxima;
    private Boolean ativo;
}
