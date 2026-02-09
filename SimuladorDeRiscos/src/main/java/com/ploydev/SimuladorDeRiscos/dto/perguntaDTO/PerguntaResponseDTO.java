package com.ploydev.SimuladorDeRiscos.dto.perguntaDTO;


import com.ploydev.SimuladorDeRiscos.entity.CategoriaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerguntaResponseDTO {

    private UUID id;
    private String pergunta;
    private CategoriaEnum categoria;
    private Boolean ativo;
}
