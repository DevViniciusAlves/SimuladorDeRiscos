package com.ploydev.SimuladorDeRiscos.dto.respostaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RespostaRequestDTO {

    private UUID avaliacaoId;
    private UUID opcaoRespostaId;
}
