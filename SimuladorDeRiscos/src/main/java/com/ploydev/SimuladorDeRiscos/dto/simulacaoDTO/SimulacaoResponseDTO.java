package com.ploydev.SimuladorDeRiscos.dto.simulacaoDTO;


import com.ploydev.SimuladorDeRiscos.entity.NivelRiscoEnum;
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
public class SimulacaoResponseDTO {

    private UUID id;
    private UUID avaliacaoId;
    private UUID tipoRiscoId;
    private NivelRiscoEnum nivelRisco;           // ← Útil pro frontend
    private String descricaoCenario;
    private Double impactoFinanceiro;
    private String impactoOperacional;
    private LocalDateTime dataCriacao;
    private Boolean ativo;
}
