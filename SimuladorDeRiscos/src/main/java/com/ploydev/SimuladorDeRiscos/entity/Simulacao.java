package com.ploydev.SimuladorDeRiscos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "simulacoes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class Simulacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Avaliacao avaliacao;
    @ManyToOne
    private TipoRisco  tipoRisco;
    @Column(length = 255)
    private String descricaoCenario;
    @Column(nullable = false)
    private double impactoFinanceiro;
    @Column(nullable = false, length = 255)
    private String impactoOperacional;
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = false)
    private Boolean ativo;


}
