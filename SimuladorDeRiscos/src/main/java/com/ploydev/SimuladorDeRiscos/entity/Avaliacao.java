package com.ploydev.SimuladorDeRiscos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "avaliacoes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "respostas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();
    @Column(nullable = false)
    private Integer scoreTotal;
    @Column(nullable = false)
    private NivelRiscoEnum nivelRisco;
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

}
