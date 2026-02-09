package com.ploydev.SimuladorDeRiscos.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name= "respostas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JsonIgnore
    private Avaliacao avaliacao;

    @ManyToOne
    private OpcaoResposta opcaoResposta;

    @Column(nullable = false)
    private Boolean ativo;
}
