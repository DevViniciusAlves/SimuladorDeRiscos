package com.ploydev.SimuladorDeRiscos.dto.avaliacaoDTO;


import com.ploydev.SimuladorDeRiscos.entity.NivelRiscoEnum;
import com.ploydev.SimuladorDeRiscos.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoResponseDTO {

    private UUID id;
    private UUID usuarioId;
    private String usuarioNome;
    private Integer porcentagemTotal;
    private NivelRiscoEnum nivelRisco;
    private Integer quantidadeRespostas;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;
}
