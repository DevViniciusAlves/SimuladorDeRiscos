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
@Table(name= "perguntas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 255)
    private String pergunta;
    @Column(nullable = false)
    private CategoriaEnum categoria;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo;


    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcaoResposta> opcoes = new ArrayList<>();
}
