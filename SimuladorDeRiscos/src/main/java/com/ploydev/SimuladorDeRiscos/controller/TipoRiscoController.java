package com.ploydev.SimuladorDeRiscos.controller;


import com.ploydev.SimuladorDeRiscos.dto.tipoRiscoDTO.TipoRiscoRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.tipoRiscoDTO.TipoRiscoResponseDTO;
import com.ploydev.SimuladorDeRiscos.entity.NivelRiscoEnum;
import com.ploydev.SimuladorDeRiscos.entity.TipoRisco;
import com.ploydev.SimuladorDeRiscos.service.TipoRiscoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tiposRiscos")
public class TipoRiscoController {

    @Autowired
    private TipoRiscoService tipoRiscoService;


    @PostMapping
    public ResponseEntity<TipoRiscoResponseDTO> criarTipoRisco(@RequestBody TipoRiscoRequestDTO dto) {
        try {
            TipoRisco tipoRisco = new TipoRisco();
            tipoRisco.setNivelRisco(dto.getNivelRisco());
            tipoRisco.setDescricao(dto.getDescricao());
            tipoRisco.setPorcentagemMinima(dto.getPorcentagemMinima());
            tipoRisco.setPorcentagemMaxima(dto.getPorcentagemMaxima());
            tipoRisco.setAtivo(true);

            TipoRisco criado = tipoRiscoService.criarTipoRisco(tipoRisco);
            TipoRiscoResponseDTO responseDTO = new TipoRiscoResponseDTO(
                    criado.getId(),
                    criado.getNivelRisco(),
                    criado.getDescricao(),
                    criado.getPorcentagemMinima(),
                    criado.getPorcentagemMaxima(),
                    criado.getAtivo()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoRiscoResponseDTO> buscarRiscoPorId(@PathVariable UUID id) {
        try {
            TipoRisco tipoRisco = tipoRiscoService.buscarRiscoPorId(id);

            TipoRiscoResponseDTO responseDTO = new TipoRiscoResponseDTO(
                    tipoRisco.getId(),
                    tipoRisco.getNivelRisco(),
                    tipoRisco.getDescricao(),
                    tipoRisco.getPorcentagemMinima(),
                    tipoRisco.getPorcentagemMaxima(),
                    tipoRisco.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TipoRiscoResponseDTO>> listarTodosOsRiscos() {
        try {
            List<TipoRisco> tiposRisco = tipoRiscoService.listarTodosOsRiscos();

            List<TipoRiscoResponseDTO> dtos = tiposRisco.stream()
                    .map(t -> new TipoRiscoResponseDTO(
                            t.getId(),
                            t.getNivelRisco(),
                            t.getDescricao(),
                            t.getPorcentagemMinima(),
                            t.getPorcentagemMaxima(),
                            t.getAtivo()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<TipoRisco> buscarPorNivel(@PathVariable NivelRiscoEnum nivel) {
        try {
            TipoRisco tipoRisco = tipoRiscoService.buscarPorNivel(nivel);
            return ResponseEntity.ok(tipoRisco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<TipoRiscoResponseDTO> atualizarTipoRisco(@PathVariable UUID id, @RequestBody TipoRiscoRequestDTO dto) {
        try {
            TipoRisco tipoRisco = new TipoRisco();
            tipoRisco.setNivelRisco(dto.getNivelRisco());
            tipoRisco.setDescricao(dto.getDescricao());
            tipoRisco.setPorcentagemMinima(dto.getPorcentagemMinima());
            tipoRisco.setPorcentagemMaxima(dto.getPorcentagemMaxima());


            TipoRisco tipoRiscoAtualizado = tipoRiscoService.atualizarTipoRisco(id, tipoRisco);

            TipoRiscoResponseDTO responseDTO = new TipoRiscoResponseDTO(
                    tipoRiscoAtualizado.getId(),
                    tipoRiscoAtualizado.getNivelRisco(),
                    tipoRiscoAtualizado.getDescricao(),
                    tipoRiscoAtualizado.getPorcentagemMinima(),
                    tipoRiscoAtualizado.getPorcentagemMaxima(),
                    tipoRiscoAtualizado.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<TipoRiscoResponseDTO> desativarTipoRisco(@PathVariable UUID id) {
        try {
            TipoRisco tipoRisco = tipoRiscoService.desativarTipoRisco(id);

            TipoRiscoResponseDTO responseDTO = new TipoRiscoResponseDTO(
                    tipoRisco.getId(),
                    tipoRisco.getNivelRisco(),
                    tipoRisco.getDescricao(),
                    tipoRisco.getPorcentagemMinima(),
                    tipoRisco.getPorcentagemMaxima(),
                    tipoRisco.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<TipoRiscoResponseDTO> ativarTipoRisco(@PathVariable UUID id) {
        try {
            TipoRisco tipoRisco = tipoRiscoService.ativarTipoRisco(id);

            TipoRiscoResponseDTO responseDTO = new TipoRiscoResponseDTO(
                    tipoRisco.getId(),
                    tipoRisco.getNivelRisco(),
                    tipoRisco.getDescricao(),
                    tipoRisco.getPorcentagemMinima(),
                    tipoRisco.getPorcentagemMaxima(),
                    tipoRisco.getAtivo()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}