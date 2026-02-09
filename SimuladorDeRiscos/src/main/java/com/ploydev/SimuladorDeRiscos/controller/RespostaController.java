package com.ploydev.SimuladorDeRiscos.controller;


import com.ploydev.SimuladorDeRiscos.dto.respostaDTO.RespostaRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.respostaDTO.RespostaResponseDTO;
import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.OpcaoResposta;
import com.ploydev.SimuladorDeRiscos.entity.Resposta;
import com.ploydev.SimuladorDeRiscos.service.AvaliacaoService;
import com.ploydev.SimuladorDeRiscos.service.OpcaoRespostaService;
import com.ploydev.SimuladorDeRiscos.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;
    @Autowired
    private AvaliacaoService avaliacaoService;
    @Autowired
    private OpcaoRespostaService opcaoRespostaService;

    @PostMapping
    public ResponseEntity<RespostaResponseDTO> criarResposta(@RequestBody RespostaRequestDTO dto) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarAvaliacaoPorId(dto.getAvaliacaoId());
            OpcaoResposta opcaoResposta = opcaoRespostaService.buscarOpcaoRespostaPorId(dto.getOpcaoRespostaId());

            Resposta criada = respostaService.criarResposta(avaliacao, opcaoResposta);

            RespostaResponseDTO responseDTO = new RespostaResponseDTO(
                    criada.getId(),
                    criada.getAvaliacao().getId(),
                    criada.getOpcaoResposta().getId(),
                    criada.getAtivo()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaResponseDTO> buscarRespostaPorId(@PathVariable UUID id) {
        try {
            Resposta r = respostaService.buscarRespostaPorId(id);

            RespostaResponseDTO responseDTO = new RespostaResponseDTO(
                    r.getId(),
                    r.getAvaliacao().getId(),
                    r.getOpcaoResposta().getId(),
                    r.getAtivo()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/avaliacao/{avaliacaoId}")
    public ResponseEntity<List<RespostaResponseDTO>> buscarRespostaPorAvaliacao(@PathVariable UUID avaliacaoId) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarAvaliacaoPorId(avaliacaoId);
            List<Resposta> respostas = respostaService.buscarRespostaPorAvaliacao(avaliacao);

            List<RespostaResponseDTO> dtos = respostas.stream()
                    .map(r -> new RespostaResponseDTO(
                            r.getId(),
                            r.getAvaliacao().getId(),
                            r.getOpcaoResposta().getId(),
                            r.getAtivo()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaResponseDTO> atualizarResposta(@PathVariable UUID id, @RequestBody RespostaRequestDTO dto) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarAvaliacaoPorId(dto.getAvaliacaoId());
            OpcaoResposta opcaoResposta = opcaoRespostaService.buscarOpcaoRespostaPorId(dto.getOpcaoRespostaId());

            Resposta dadosNovos = new Resposta();
            dadosNovos.setAvaliacao(avaliacao);
            dadosNovos.setOpcaoResposta(opcaoResposta);

            Resposta respostaAtualizada = respostaService.atualizarResposta(id, dadosNovos);

            RespostaResponseDTO responseDTO = new RespostaResponseDTO(
                    respostaAtualizada.getId(),
                    respostaAtualizada.getAvaliacao().getId(),
                    respostaAtualizada.getOpcaoResposta().getId(),
                    respostaAtualizada.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<RespostaResponseDTO> desativarResposta(@PathVariable UUID id) {
        try {
            Resposta resposta = respostaService.desativarResposta(id);
            RespostaResponseDTO responseDTO = new RespostaResponseDTO(
                    resposta.getId(),
                    resposta.getAvaliacao().getId(),
                    resposta.getOpcaoResposta().getId(),
                    resposta.getAtivo()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<RespostaResponseDTO> ativarResposta(@PathVariable UUID id) {
        try {
            Resposta resposta = respostaService.ativarResposta(id);
            RespostaResponseDTO responseDTO = new RespostaResponseDTO(
                    resposta.getId(),
                    resposta.getAvaliacao().getId(),
                    resposta.getOpcaoResposta().getId(),
                    resposta.getAtivo()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
