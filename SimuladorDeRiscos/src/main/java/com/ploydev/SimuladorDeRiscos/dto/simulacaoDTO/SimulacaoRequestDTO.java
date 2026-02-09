package com.ploydev.SimuladorDeRiscos.dto.simulacaoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SimulacaoRequestDTO {

    private UUID avaliacaoId;
}
