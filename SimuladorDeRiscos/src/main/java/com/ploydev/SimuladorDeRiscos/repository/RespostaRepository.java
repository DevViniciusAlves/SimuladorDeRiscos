package com.ploydev.SimuladorDeRiscos.repository;

import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.Pergunta;
import com.ploydev.SimuladorDeRiscos.entity.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface RespostaRepository extends JpaRepository<Resposta, UUID> {
    List<Resposta> findByAvaliacao(Avaliacao avaliacao);
    Avaliacao findByAvaliacao(String avaliacao);
}
