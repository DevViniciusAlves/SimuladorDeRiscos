package com.ploydev.SimuladorDeRiscos.service;

import com.ploydev.SimuladorDeRiscos.entity.OpcaoResposta;
import com.ploydev.SimuladorDeRiscos.entity.Pergunta;
import com.ploydev.SimuladorDeRiscos.repository.OpcaoRespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OpcaoRespostaService {

    @Autowired
    private OpcaoRespostaRepository opcaoRespostaRepository;

    public OpcaoResposta criarOpcao(OpcaoResposta opcao) throws Exception {
        OpcaoResposta opcaoExistente = opcaoRespostaRepository.findByTexto(opcao.getTexto());

        if (opcaoExistente != null) {
            throw new Exception("Opção já existente");
        }

        opcao.setAtivo(true);
        return opcaoRespostaRepository.save(opcao);
    }

    public OpcaoResposta buscarOpcaoRespostaPorId(UUID id) {
        return opcaoRespostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opção com o" + id + "não existe"));
    }

    public List<OpcaoResposta> buscarOpcoesDaPergunta(Pergunta pergunta) {
        return opcaoRespostaRepository.findByPergunta(pergunta);
    }

    public OpcaoResposta atualizarOpcao(UUID id, OpcaoResposta opcaoAtualizada) {
        OpcaoResposta atualizarOpcao = buscarOpcaoRespostaPorId(id);
        atualizarOpcao.setTexto(opcaoAtualizada.getTexto());
        atualizarOpcao.setPorcentagem(opcaoAtualizada.getPorcentagem());

        return opcaoRespostaRepository.save(atualizarOpcao);
    }

    public OpcaoResposta desativarOpcao(UUID id) {
        OpcaoResposta opcaoResposta = opcaoRespostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opção com" + id + "não encontrada"));

        opcaoResposta.setAtivo(false);
        return opcaoRespostaRepository.save(opcaoResposta);
    }

    public OpcaoResposta ativarOpcao(UUID id) {
        OpcaoResposta opcaoResposta = opcaoRespostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opção com" + id + "não encontrada"));

        opcaoResposta.setAtivo(true);
        return opcaoRespostaRepository.save(opcaoResposta);
    }
}
