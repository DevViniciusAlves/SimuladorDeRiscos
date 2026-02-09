package com.ploydev.SimuladorDeRiscos.dto.perguntaDTO;


import com.ploydev.SimuladorDeRiscos.entity.CategoriaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerguntaRequestDTO {

    private String pergunta;
    private CategoriaEnum categoria;
}
