package com.ploydev.SimuladorDeRiscos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "opcaoResposta")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class OpcaoResposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String texto;

    @Column(nullable = false)
    private Integer porcentagem;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo;

    @ManyToOne
    private Pergunta pergunta;

}
