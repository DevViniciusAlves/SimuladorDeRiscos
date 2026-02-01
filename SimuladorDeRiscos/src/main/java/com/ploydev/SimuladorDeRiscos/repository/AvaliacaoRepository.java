package com.ploydev.SimuladorDeRiscos.repository;

import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, UUID> {
    List<Avaliacao> findByUsuario(Usuario usuario);
}
