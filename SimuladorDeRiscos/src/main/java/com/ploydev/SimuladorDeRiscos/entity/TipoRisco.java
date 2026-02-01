package com.ploydev.SimuladorDeRiscos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "típoRisco")
@Getter @Setter
@NoArgsConstructor  @AllArgsConstructor

public class TipoRisco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 255)
    private NivelRiscoEnum nivelRisco;
    @Column(length = 255)
    private String descricao;
    @Column(nullable = false)
    private Integer porcentagemMinima;
    @Column(nullable = false)
    private Integer porcentagemMaxima;
    @Column(nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "tipoRisco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Simulacao> simulacoes  = new ArrayList<>();
}

