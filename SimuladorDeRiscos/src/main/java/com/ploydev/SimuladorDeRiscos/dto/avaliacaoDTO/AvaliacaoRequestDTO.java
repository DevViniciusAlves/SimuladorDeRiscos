package com.ploydev.SimuladorDeRiscos.dto.avaliacaoDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoRequestDTO {

    private UUID usuarioId;
}
