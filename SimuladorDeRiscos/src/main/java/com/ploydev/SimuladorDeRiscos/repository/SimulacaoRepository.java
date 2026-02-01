package com.ploydev.SimuladorDeRiscos.repository;

import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao, UUID> {
    List<Simulacao> findByAvaliacao(Avaliacao avaliacao);
}
