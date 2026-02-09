package com.ploydev.SimuladorDeRiscos.controller;


import com.ploydev.SimuladorDeRiscos.dto.perguntaDTO.PerguntaRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.perguntaDTO.PerguntaResponseDTO;
import com.ploydev.SimuladorDeRiscos.entity.Pergunta;
import com.ploydev.SimuladorDeRiscos.service.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/perguntas")
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;


    @PostMapping
    public ResponseEntity<PerguntaResponseDTO> criarPergunta(@RequestBody PerguntaRequestDTO dto){
        try{
            Pergunta pergunta = new Pergunta();
            pergunta.setPergunta(dto.getPergunta());
            pergunta.setCategoria(dto.getCategoria());
            pergunta.setAtivo(true);

            Pergunta criada = perguntaService.criarPergunta(pergunta);

            PerguntaResponseDTO responseDTO = new PerguntaResponseDTO(
                    criada.getId(),
                    criada.getPergunta(),
                    criada.getCategoria(),
                    criada.getAtivo()

            );
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/ativas")
    public ResponseEntity<List<PerguntaResponseDTO>> buscarPerguntasAtivas(){
        try {
           List<PerguntaResponseDTO> perguntas = perguntaService.buscarPerguntasAtivas()
                   .stream()
                   .map(p -> new PerguntaResponseDTO(
                           p.getId(),
                           p.getPergunta(),
                           p.getCategoria(),
                           p.getAtivo()
                   ))
                   .toList();
           return ResponseEntity.ok(perguntas);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<PerguntaResponseDTO>> buscarTodasAsPerguntas(){
        try{
            List<PerguntaResponseDTO> perguntas = perguntaService.buscarTodasAsPerguntas()
                    .stream()
                    .map(p -> new PerguntaResponseDTO(
                            p.getId(),
                            p.getPergunta(),
                            p.getCategoria(),
                            p.getAtivo()
                    ))
                    .toList();

            return ResponseEntity.ok(perguntas);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<PerguntaResponseDTO> buscarPerguntaPorId(@PathVariable UUID id){
        try{
            Pergunta p = perguntaService.buscarPerguntaPorId(id);

            PerguntaResponseDTO responseDTO = new PerguntaResponseDTO(
                    p.getId(),
                    p.getPergunta(),
                    p.getCategoria(),
                    p.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/desativar")
    public ResponseEntity<PerguntaResponseDTO> desativarPergunta(@PathVariable UUID id){
        try{
            Pergunta p = perguntaService.desativarPergunta(id);

            PerguntaResponseDTO response = new PerguntaResponseDTO(
                    p.getId(),
                    p.getPergunta(),
                    p.getCategoria(),
                    p.getAtivo()
            );

            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/ativar")
    public ResponseEntity<PerguntaResponseDTO> ativarPergunta(@PathVariable UUID id){
        try{
            Pergunta p = perguntaService.ativarPergunta(id);

            PerguntaResponseDTO response = new PerguntaResponseDTO(
                    p.getId(),
                    p.getPergunta(),
                    p.getCategoria(),
                    p.getAtivo()
            );

            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
