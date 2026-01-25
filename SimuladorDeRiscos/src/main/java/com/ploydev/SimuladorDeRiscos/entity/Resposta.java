package com.ploydev.SimuladorDeRiscos.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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
    private Pergunta pergunta;
    @ManyToOne
    private Avaliacao avaliacao;
    @Column(nullable = false, length = 255)
    private String resposta;
    @Column(nullable = false)
    private Integer score;
}
