package com.ploydev.SimuladorDeRiscos.service;


import com.ploydev.SimuladorDeRiscos.entity.Pergunta;
import com.ploydev.SimuladorDeRiscos.entity.Usuario;
import com.ploydev.SimuladorDeRiscos.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    public Pergunta criarPergunta(Pergunta pergunta) throws Exception {
        Pergunta perguntaExistente = perguntaRepository.findByPergunta(pergunta.getPergunta());

        if (perguntaExistente != null) {
            throw new Exception("Pergunta já existente");

        }
        return perguntaRepository.save(pergunta);
    }

    public Pergunta buscarPerguntaPorId(UUID id) {
        return perguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pergunta com o" + id + "não foi encontrada"));
    }

    public List<Pergunta> buscarTodasAsPerguntas() {
        return perguntaRepository.findAll();
    }

    public Pergunta atualizarPergunta(UUID id, Pergunta perguntaAtualizada) {
        Pergunta atualizarPergunta = buscarPerguntaPorId(id);
        atualizarPergunta.setPergunta(perguntaAtualizada.getPergunta());
        atualizarPergunta.setCategoria(perguntaAtualizada.getCategoria());

        return perguntaRepository.save(atualizarPergunta);
    }

    public Pergunta desativarPergunta(UUID id) {
        Pergunta pergunta = perguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pergunta com" + id + "não foi encontrada"));

        pergunta.setAtivo(false);
        perguntaRepository.save(pergunta);

        return perguntaRepository.save(pergunta);
    }
    public Pergunta ativarPergunta(UUID id){
        Pergunta pergunta = perguntaRepository.findById(id)
                .orElseThrow (()-> new RuntimeException("Pergunta com" + id + "não foi encontrada"));

        pergunta.setAtivo(true);
        perguntaRepository.save(pergunta);

        return perguntaRepository.save(pergunta);
}
    public List<Pergunta> buscarPerguntasAtivas(){
        return perguntaRepository.findByAtivoTrue();
    }

}
