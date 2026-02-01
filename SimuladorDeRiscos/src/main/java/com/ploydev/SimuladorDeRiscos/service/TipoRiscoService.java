package com.ploydev.SimuladorDeRiscos.service;

import com.ploydev.SimuladorDeRiscos.entity.TipoRisco;
import com.ploydev.SimuladorDeRiscos.repository.TipoRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoRiscoService {

    @Autowired
    private TipoRiscoRepository tipoRiscoRepository;

    public TipoRisco criarTipoRisco(TipoRisco tipoRisco) throws Exception {
        TipoRisco tipoExistente = tipoRiscoRepository.findByNivelRisco(tipoRisco.getNivelRisco().toString());

        if (tipoExistente != null){
            throw new Exception("Tipo de risco já existe");
        }
        if (tipoRisco.getPorcentagemMinima() < 0 || tipoRisco.getPorcentagemMaxima() > 100) {
            throw new Exception("Porcentagem invalida");
        }
        if (tipoRisco.getPorcentagemMinima() >= tipoRisco.getPorcentagemMaxima()){
            throw new Exception("Porcentagem minima não pode ser maior ou igual a Porcentagem maxima");
        }
        return tipoRiscoRepository.save(tipoRisco);
    }
    public TipoRisco buscarRiscoPorId(UUID id){
        return tipoRiscoRepository.findById(id)
                .orElseThrow (()-> new RuntimeException("Nivel risco com id" + id + "não encontrado"));
    }
    public List<TipoRisco> listarTodosOsRiscos(){
        return tipoRiscoRepository.findAll();
    }
    public TipoRisco buscarPorNivel(String nivelRisco){
        return tipoRiscoRepository.findByNivelRisco(nivelRisco);
    }
    public TipoRisco atualizarTipoRisco(UUID id, TipoRisco tipoRiscoAtualizado){
        TipoRisco atualizarTipoRisco = buscarRiscoPorId(id);
        atualizarTipoRisco.setNivelRisco(tipoRiscoAtualizado.getNivelRisco());
        atualizarTipoRisco.setDescricao(tipoRiscoAtualizado.getDescricao());
        atualizarTipoRisco.setPorcentagemMinima(tipoRiscoAtualizado.getPorcentagemMinima());
        atualizarTipoRisco.setPorcentagemMaxima(tipoRiscoAtualizado.getPorcentagemMaxima());

        return tipoRiscoRepository.save(atualizarTipoRisco);
    }
    public void desativarTipoRisco(UUID id){
        TipoRisco tipoRisco = tipoRiscoRepository.findById(id)
                .orElseThrow (()-> new RuntimeException("Tipo risco com id" + id + "não encontrado"));

        tipoRisco.setAtivo(false);
        tipoRiscoRepository.save(tipoRisco);
    }
}
