package com.ploydev.SimuladorDeRiscos.dto.tipoRiscoDTO;

import com.ploydev.SimuladorDeRiscos.entity.NivelRiscoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoRiscoRequestDTO {

    private NivelRiscoEnum nivelRisco;
    private String descricao;
    private Integer porcentagemMinima;
    private Integer porcentagemMaxima;
}
