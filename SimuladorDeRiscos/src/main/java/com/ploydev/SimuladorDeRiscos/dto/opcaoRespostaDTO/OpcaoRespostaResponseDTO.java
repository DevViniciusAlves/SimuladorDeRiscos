package com.ploydev.SimuladorDeRiscos.dto.opcaoRespostaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OpcaoRespostaResponseDTO {

    private UUID id;
    private String texto;
    private Integer porcentagem;
    private Boolean ativo;
    private UUID perguntaId;


}
