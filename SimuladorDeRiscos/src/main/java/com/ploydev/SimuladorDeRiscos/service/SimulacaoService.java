package com.ploydev.SimuladorDeRiscos.service;

import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.Simulacao;
import com.ploydev.SimuladorDeRiscos.entity.TipoRisco;
import com.ploydev.SimuladorDeRiscos.repository.SimulacaoRepository;
import com.ploydev.SimuladorDeRiscos.repository.TipoRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SimulacaoService {

    @Autowired
    private SimulacaoRepository simulacaoRepository;
    @Autowired
    private TipoRiscoRepository tipoRiscoRepository;

    public Simulacao criarSimulacao(Avaliacao avaliacao){
        Simulacao simulacao = new Simulacao();
        simulacao.setAvaliacao(avaliacao);

        TipoRisco tipoRisco = tipoRiscoRepository.findByNivelRisco(avaliacao.getNivelRisco().toString());
        simulacao.setTipoRisco(tipoRisco);

        String descricao = "Cenario de ataque" + avaliacao.getNivelRisco();
        simulacao.setDescricaoCenario(descricao);

        Integer impactoFinanceiro = avaliacao.getPorcentagemTotal() * 100;
        simulacao.setImpactoFinanceiro(impactoFinanceiro);

        String impactoOperacional = "Paralisaçao do sistema" + avaliacao.getPorcentagemTotal() * 100;

        simulacao.setImpactoOperacional(impactoOperacional);

        return simulacaoRepository.save(simulacao);

    }
    public Simulacao buscarSimulacaoPorId(UUID id){
        return simulacaoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Simulação com id" + id + "não encontrada"));
    }
    public List<Simulacao> buscarSimulacaoDeUmaAvalicao(Avaliacao avaliacao){
        return simulacaoRepository.findByAvaliacao(avaliacao);
    }
    public void desativarSimulacao(UUID id){
        Simulacao simulacao = simulacaoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Simulação com id" + id +"não encontrada"));

        simulacao.setAtivo(false);
        simulacaoRepository.save(simulacao);

    }
}
