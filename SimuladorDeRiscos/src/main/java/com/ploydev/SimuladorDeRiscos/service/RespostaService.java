package com.ploydev.SimuladorDeRiscos.service;


import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.OpcaoResposta;
import com.ploydev.SimuladorDeRiscos.entity.Resposta;
import com.ploydev.SimuladorDeRiscos.repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    public Resposta criarResposta(Avaliacao avaliacao, OpcaoResposta opcaoResposta){
        Resposta resposta = new Resposta();
        // ADD Validação
        resposta.setAvaliacao(avaliacao);
        // ADD a opcao de resposta
        resposta.setOpcaoResposta(opcaoResposta);

        return respostaRepository.save(resposta);
    }
    public Resposta buscarRespostaPorId(UUID id){
        return respostaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Resposta por" + id + "não encontrada"));
    }
    public List<Resposta> buscarRespostaPorAvaliacao(Avaliacao avaliacao){
        return respostaRepository.findByAvaliacao(avaliacao);
    }
    public Resposta atualizarResposta(UUID id, Resposta respostaAtualizada){
        Resposta atualizarResposta = buscarRespostaPorId(id);
        atualizarResposta.setAvaliacao(respostaAtualizada.getAvaliacao());
        atualizarResposta.setOpcaoResposta(respostaAtualizada.getOpcaoResposta());

        return respostaRepository.save(atualizarResposta);
    }
    public void desativarResposta(UUID id){
        Resposta resposta = respostaRepository.findById(id)
                .orElseThrow (()-> new RuntimeException("Resposta com" + id + "não encontrada"));

        resposta.setAtivo(false);
        respostaRepository.save(resposta);
    }

}
