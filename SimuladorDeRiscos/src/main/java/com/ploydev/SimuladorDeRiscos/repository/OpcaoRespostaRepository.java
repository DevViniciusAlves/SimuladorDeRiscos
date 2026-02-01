package com.ploydev.SimuladorDeRiscos.repository;

import com.ploydev.SimuladorDeRiscos.entity.OpcaoResposta;
import com.ploydev.SimuladorDeRiscos.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OpcaoRespostaRepository extends JpaRepository<OpcaoResposta, UUID>{
    List<OpcaoResposta> findByPergunta(Pergunta pergunta);
    OpcaoResposta findByTexto(String texto);
}
