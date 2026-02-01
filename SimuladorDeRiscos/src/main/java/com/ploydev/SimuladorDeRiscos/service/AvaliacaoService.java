package com.ploydev.SimuladorDeRiscos.service;

import com.ploydev.SimuladorDeRiscos.entity.*;
import com.ploydev.SimuladorDeRiscos.repository.AvaliacaoRepository;
import com.ploydev.SimuladorDeRiscos.repository.TipoRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AvaliacaoService {

    @Autowired
    private TipoRiscoRepository tipoRiscoRepository;
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public Avaliacao criarAvaliacao(Usuario usuario) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setUsuario(usuario);
        avaliacao.setPorcentagemTotal(0);
        avaliacao.setDataCriacao(LocalDateTime.now());
        avaliacao.setNivelRisco(null);

        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao buscarAvaliacaoPorId(UUID id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação com id" + id + "não encontrado"));
    }

    public List<Avaliacao> buscarAvaliacaoPorUsuario(Usuario usuario) {
        return avaliacaoRepository.findByUsuario(usuario);
    }

    public Integer calcularNivelRisco(Avaliacao avaliacao) {
        List<Resposta> respostas = avaliacao.getRespostas();

        if (respostas.isEmpty()) {  // VERIFICA SE TEM RESPOSTA
            return 0;
        }
        Integer somaTotal = 0;
        for (Resposta resposta : respostas) {
            somaTotal += resposta.getOpcaoResposta().getPorcentagem();
        }
        Integer media = somaTotal / respostas.size();

        return media;
    }

    public NivelRiscoEnum determinarNivelRisco(Integer nivel) {
        List<TipoRisco> tiposRisco = tipoRiscoRepository.findAll();

        for (TipoRisco tipo : tiposRisco) {
            if (nivel >= tipo.getPorcentagemMinima() && nivel <= tipo.getPorcentagemMaxima()) {
                return tipo.getNivelRisco();
            }
        }
        throw new RuntimeException("Nenhum nivel foi encontrado com" + nivel);
    }
    public Avaliacao finalizarAvaliacao(Avaliacao avaliacao){
        Integer nivel = calcularNivelRisco(avaliacao); // CHAMANDO O METODO PARA CALCULAR

        NivelRiscoEnum nivelRisco = determinarNivelRisco(nivel);

        avaliacao.setPorcentagemTotal(nivel);
        avaliacao.setNivelRisco(nivelRisco);

        return avaliacaoRepository.save(avaliacao);
    }
}
