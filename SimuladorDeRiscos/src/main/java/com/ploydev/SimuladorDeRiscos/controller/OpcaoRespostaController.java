package com.ploydev.SimuladorDeRiscos.controller;

import com.ploydev.SimuladorDeRiscos.dto.avaliacaoDTO.AvaliacaoRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.opcaoRespostaDTO.OpcaoRespostaRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.opcaoRespostaDTO.OpcaoRespostaResponseDTO;
import com.ploydev.SimuladorDeRiscos.entity.OpcaoResposta;
import com.ploydev.SimuladorDeRiscos.entity.Pergunta;
import com.ploydev.SimuladorDeRiscos.service.OpcaoRespostaService;
import com.ploydev.SimuladorDeRiscos.service.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/opcoes")
public class OpcaoRespostaController {

    @Autowired
    private OpcaoRespostaService opcaoRespostaService;
    @Autowired
    private PerguntaService perguntaService;


    @PostMapping("/{perguntaId}")
    public ResponseEntity<OpcaoRespostaResponseDTO> criarOpcao(@RequestBody OpcaoRespostaRequestDTO dto) {
        try {
            Pergunta pergunta = perguntaService.buscarPerguntaPorId(dto.getPerguntaId());

            OpcaoResposta opcao = new OpcaoResposta();
            opcao.setTexto(dto.getTexto());
            opcao.setPorcentagem(dto.getPorcentagem());
            opcao.setPergunta(pergunta);
            opcao.setAtivo(true);

            OpcaoResposta criada = opcaoRespostaService.criarOpcao(opcao);

            OpcaoRespostaResponseDTO responseDTO = new OpcaoRespostaResponseDTO(
                    criada.getId(),
                    criada.getTexto(),
                    criada.getPorcentagem(),
                    criada.getAtivo(),
                    criada.getPergunta().getId()

            );
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/pergunta/{perguntaId}")
    public ResponseEntity<List<OpcaoRespostaResponseDTO>> buscarOpcoesDaPergunta(@PathVariable UUID perguntaId) {
        try {
            Pergunta pergunta = perguntaService.buscarPerguntaPorId(perguntaId);

            List<OpcaoResposta> opcoes = opcaoRespostaService.buscarOpcoesDaPergunta(pergunta);

            List<OpcaoRespostaResponseDTO> dtos = opcoes.stream()
                    .map(o -> new OpcaoRespostaResponseDTO(
                            o.getId(),
                            o.getTexto(),
                            o.getPorcentagem(),
                            o.getAtivo(),
                            o.getPergunta().getId()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<OpcaoRespostaResponseDTO> buscarOpcaoRespostaPorId(@PathVariable UUID id) {
        try {
            OpcaoResposta opcaoResposta = opcaoRespostaService.buscarOpcaoRespostaPorId(id);

            OpcaoRespostaResponseDTO responseDTO = new OpcaoRespostaResponseDTO(
                    opcaoResposta.getId(),
                    opcaoResposta.getTexto(),
                    opcaoResposta.getPorcentagem(),
                    opcaoResposta.getAtivo(),
                    opcaoResposta.getPergunta().getId()

            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/desativar")
    public ResponseEntity<OpcaoRespostaResponseDTO> desativarOpcao(@PathVariable UUID id) {
        try {
            OpcaoResposta opcao = opcaoRespostaService.desativarOpcao(id);

            OpcaoRespostaResponseDTO responseDTO = new OpcaoRespostaResponseDTO(
                    opcao.getId(),
                    opcao.getTexto(),
                    opcao.getPorcentagem(),
                    opcao.getAtivo(),
                    opcao.getPergunta().getId()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/ativar")
    public ResponseEntity<OpcaoRespostaResponseDTO> ativarOpcao(@PathVariable UUID id) {
        try {
            OpcaoResposta opcao = opcaoRespostaService.ativarOpcao(id);

            OpcaoRespostaResponseDTO responseDTO = new OpcaoRespostaResponseDTO(
                    opcao.getId(),
                    opcao.getTexto(),
                    opcao.getPorcentagem(),
                    opcao.getAtivo(),
                    opcao.getPergunta().getId()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/atualizar")
    public ResponseEntity<OpcaoRespostaResponseDTO> atualizarOpcao(
            @PathVariable UUID id,
            @RequestBody OpcaoRespostaRequestDTO dto) {
        try {
            OpcaoResposta opcao = opcaoRespostaService.buscarOpcaoRespostaPorId(id);


            opcao.setTexto(dto.getTexto());
            opcao.setPorcentagem(dto.getPorcentagem());

            if (dto.getPerguntaId() != null) {
                Pergunta pergunta = perguntaService.buscarPerguntaPorId(dto.getPerguntaId());
                opcao.setPergunta(pergunta);
            }

            OpcaoResposta opcaoAtualizada = opcaoRespostaService.atualizarOpcao(id, opcao);

            OpcaoRespostaResponseDTO responseDTO = new OpcaoRespostaResponseDTO(
                    opcaoAtualizada.getId(),
                    opcaoAtualizada.getTexto(),
                    opcaoAtualizada.getPorcentagem(),
                    opcaoAtualizada.getAtivo(),
                    opcaoAtualizada.getPergunta().getId()
            );

            return ResponseEntity.ok(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}