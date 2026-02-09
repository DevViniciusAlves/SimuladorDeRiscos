package com.ploydev.SimuladorDeRiscos.repository;

import com.ploydev.SimuladorDeRiscos.entity.NivelRiscoEnum;
import com.ploydev.SimuladorDeRiscos.entity.TipoRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TipoRiscoRepository extends JpaRepository<TipoRisco, UUID> {
    TipoRisco findByNivelRisco(NivelRiscoEnum nivelRisco);
}