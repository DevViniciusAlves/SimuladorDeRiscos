package com.ploydev.SimuladorDeRiscos.controller;


import com.ploydev.SimuladorDeRiscos.dto.simulacaoDTO.SimulacaoRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.simulacaoDTO.SimulacaoResponseDTO;
import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.Simulacao;
import com.ploydev.SimuladorDeRiscos.service.AvaliacaoService;
import com.ploydev.SimuladorDeRiscos.service.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simulacoes")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;
    @Autowired
    private AvaliacaoService avaliacaoService;


    @PostMapping
    public ResponseEntity<SimulacaoResponseDTO> criarSimulacao(@RequestBody SimulacaoRequestDTO dto) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarAvaliacaoPorId(dto.getAvaliacaoId());
            Simulacao simulacao = simulacaoService.criarSimulacao(avaliacao);

            SimulacaoResponseDTO responseDTO = new SimulacaoResponseDTO(
                    simulacao.getId(),
                    simulacao.getAvaliacao().getId(),
                    simulacao.getTipoRisco().getId(),
                    simulacao.getTipoRisco().getNivelRisco(),
                    simulacao.getDescricaoCenario(),
                    simulacao.getImpactoFinanceiro(),
                    simulacao.getImpactoOperacional(),
                    simulacao.getDataCriacao(),
                    simulacao.getAtivo()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulacaoResponseDTO> buscarSimulacaoPorId(@PathVariable UUID id) {
        try {
           Simulacao s = simulacaoService.buscarSimulacaoPorId(id);
           SimulacaoResponseDTO responseDTO = new SimulacaoResponseDTO(
                   s.getId(),
                   s.getAvaliacao().getId(),
                   s.getTipoRisco().getId(),
                   s.getTipoRisco().getNivelRisco(),
                   s.getDescricaoCenario(),
                   s.getImpactoFinanceiro(),
                   s.getImpactoOperacional(),
                   s.getDataCriacao(),
                   s.getAtivo()

           );
           return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/avaliacao/{avaliacaoId}")
    public ResponseEntity<List<SimulacaoResponseDTO>> buscarSimulacaoDeUmaAvaliacao(@PathVariable UUID avaliacaoId) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarAvaliacaoPorId(avaliacaoId);
            List<Simulacao> simulacoes = simulacaoService.buscarSimulacaoDeUmaAvaliacao(avaliacao);

            List<SimulacaoResponseDTO> dtos = simulacoes.stream()
                    .map(s -> new SimulacaoResponseDTO(
                            s.getId(),
                            s.getAvaliacao().getId(),
                            s.getTipoRisco().getId(),
                            s.getTipoRisco().getNivelRisco(),
                            s.getDescricaoCenario(),
                            s.getImpactoFinanceiro(),
                            s.getImpactoOperacional(),
                            s.getDataCriacao(),
                            s.getAtivo()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<SimulacaoResponseDTO> desativarSimulacao(@PathVariable UUID id) {
        try {
            Simulacao simulacao = simulacaoService.desativarSimulacao(id);
            SimulacaoResponseDTO responseDTO = new SimulacaoResponseDTO(
                    simulacao.getId(),
                    simulacao.getAvaliacao().getId(),
                    simulacao.getTipoRisco().getId(),
                    simulacao.getTipoRisco().getNivelRisco(),
                    simulacao.getDescricaoCenario(),
                    simulacao.getImpactoFinanceiro(),
                    simulacao.getImpactoOperacional(),
                    simulacao.getDataCriacao(),
                    simulacao.getAtivo()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<SimulacaoResponseDTO> ativarSimulacao(@PathVariable UUID id) {
        try {
            Simulacao simulacao = simulacaoService.ativarSimulacao(id);
            SimulacaoResponseDTO responseDTO = new SimulacaoResponseDTO(
                    simulacao.getId(),
                    simulacao.getAvaliacao().getId(),
                    simulacao.getTipoRisco().getId(),
                    simulacao.getTipoRisco().getNivelRisco(),
                    simulacao.getDescricaoCenario(),
                    simulacao.getImpactoFinanceiro(),
                    simulacao.getImpactoOperacional(),
                    simulacao.getDataCriacao(),
                    simulacao.getAtivo()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}