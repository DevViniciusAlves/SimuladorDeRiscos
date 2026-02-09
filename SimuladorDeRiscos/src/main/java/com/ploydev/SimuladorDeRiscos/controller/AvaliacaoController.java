package com.ploydev.SimuladorDeRiscos.controller;

import com.ploydev.SimuladorDeRiscos.dto.avaliacaoDTO.AvaliacaoRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.avaliacaoDTO.AvaliacaoResponseDTO;
import com.ploydev.SimuladorDeRiscos.entity.Avaliacao;
import com.ploydev.SimuladorDeRiscos.entity.Usuario;
import com.ploydev.SimuladorDeRiscos.service.AvaliacaoService;
import com.ploydev.SimuladorDeRiscos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@RequestBody AvaliacaoRequestDTO dto) {
        try {
           Usuario usuario = usuarioService.buscarPorId(dto.getUsuarioId());

           Avaliacao avaliacao = avaliacaoService.criarAvaliacao(usuario);

            AvaliacaoResponseDTO responseDTO = new AvaliacaoResponseDTO(
                    avaliacao.getId(),
                    avaliacao.getUsuario().getId(),
                    avaliacao.getUsuario().getNome(),
                    avaliacao.getPorcentagemTotal(),
                    avaliacao.getNivelRisco(),
                    avaliacao.getRespostas().size(),
                    avaliacao.getDataCriacao(),
                    avaliacao.getDataAtualizacao(),
                    avaliacao.getAtivo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<AvaliacaoResponseDTO> finalizarAvaliacao(@PathVariable UUID id) {
        try {
            Avaliacao avaliacaoFinalizada = avaliacaoService.finalizarAvaliacao(id);

            AvaliacaoResponseDTO responseDTO = new AvaliacaoResponseDTO(
                    avaliacaoFinalizada.getId(),
                    avaliacaoFinalizada.getUsuario().getId(),
                    avaliacaoFinalizada.getUsuario().getNome(),
                    avaliacaoFinalizada.getPorcentagemTotal(),
                    avaliacaoFinalizada.getNivelRisco(),
                    avaliacaoFinalizada.getRespostas().size(),
                    avaliacaoFinalizada.getDataCriacao(),
                    avaliacaoFinalizada.getDataAtualizacao(),
                    avaliacaoFinalizada.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarAvaliacaoPorId(@PathVariable UUID id) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarAvaliacaoPorId(id);

            AvaliacaoResponseDTO responseDTO = new AvaliacaoResponseDTO(
                    avaliacao.getId(),
                    avaliacao.getUsuario().getId(),
                    avaliacao.getUsuario().getNome(),
                    avaliacao.getPorcentagemTotal(),
                    avaliacao.getNivelRisco(),
                    avaliacao.getRespostas().size(),
                    avaliacao.getDataCriacao(),
                    avaliacao.getDataAtualizacao(),
                    avaliacao.getAtivo()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> buscarAvaliacaoPorUsuario(@PathVariable UUID usuarioId) {
        try {
            Usuario usuario = usuarioService.buscarPorId(usuarioId);
            List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacaoPorUsuario(usuario);

            List<AvaliacaoResponseDTO> dtos = avaliacoes.stream()
                    .map(a -> new AvaliacaoResponseDTO(
                            a.getId(),
                            a.getUsuario().getId(),
                            a.getUsuario().getNome(),
                            a.getPorcentagemTotal(),
                            a.getNivelRisco(),
                            a.getRespostas().size(),
                            a.getDataCriacao(),
                            a.getDataAtualizacao(),
                            a.getAtivo()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<AvaliacaoResponseDTO> desativarAvaliacao(@PathVariable UUID id) {
        try {
            Avaliacao avaliacao = avaliacaoService.desativarAvaliacao(id);

            AvaliacaoResponseDTO responseDTO = new AvaliacaoResponseDTO(
                    avaliacao.getId(),
                    avaliacao.getUsuario().getId(),
                    avaliacao.getUsuario().getNome(),
                    avaliacao.getPorcentagemTotal(),
                    avaliacao.getNivelRisco(),
                    avaliacao.getRespostas().size(),
                    avaliacao.getDataCriacao(),
                    avaliacao.getDataAtualizacao(),
                    avaliacao.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/ativar")
    public ResponseEntity<AvaliacaoResponseDTO> ativarAvaliacao(@PathVariable UUID id) {
        try {
            Avaliacao avaliacao = avaliacaoService.ativarAvaliacao(id);

            AvaliacaoResponseDTO responseDTO = new AvaliacaoResponseDTO(
                    avaliacao.getId(),
                    avaliacao.getUsuario().getId(),
                    avaliacao.getUsuario().getNome(),
                    avaliacao.getPorcentagemTotal(),
                    avaliacao.getNivelRisco(),
                    avaliacao.getRespostas().size(),
                    avaliacao.getDataCriacao(),
                    avaliacao.getDataAtualizacao(),
                    avaliacao.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

