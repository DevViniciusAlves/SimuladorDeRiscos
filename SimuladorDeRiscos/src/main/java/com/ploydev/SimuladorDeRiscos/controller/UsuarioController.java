package com.ploydev.SimuladorDeRiscos.controller;

import com.ploydev.SimuladorDeRiscos.dto.usuarioDTO.UsuarioRequestDTO;
import com.ploydev.SimuladorDeRiscos.dto.usuarioDTO.UsuarioResponseDTO;
import com.ploydev.SimuladorDeRiscos.entity.Usuario;
import com.ploydev.SimuladorDeRiscos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO dto){
        try{
            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setCpf(dto.getCpf());
            usuario.setSenha(dto.getSenha());

            Usuario criado = usuarioService.criarUsuario(usuario);

            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                    criado.getId(),
                    criado.getNome(),
                    criado.getEmail(),
                    criado.getCpf(),
                    criado.getAtivo(),
                    criado.getDataCadastro()
        );
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getAtivo(),
                    usuario.getDataCadastro()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/email")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@RequestParam String email){
        try{
            Usuario usuario = usuarioService.buscarPorEmail(email);
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getAtivo(),
                    usuario.getDataCadastro()
            );
            return ResponseEntity.ok(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodosUsuarios(){
        try{
            List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();
            System.out.println("Usuários encontrados: " + usuarios.size());
            List<UsuarioResponseDTO> dtos = usuarios.stream()
                    .map(u -> new UsuarioResponseDTO(
                            u.getId(),
                            u.getNome(),
                            u.getEmail(),
                            u.getCpf(),
                            u.getAtivo(),
                            u.getDataCadastro()
                    ))
                    .toList();
            return ResponseEntity.ok(dtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable UUID id,@RequestBody UsuarioRequestDTO dto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setCpf(dto.getCpf());
            usuario.setSenha(dto.getSenha());

            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                    usuarioAtualizado.getId(),
                    usuarioAtualizado.getNome(),
                    usuarioAtualizado.getEmail(),
                    usuarioAtualizado.getCpf(),
                    usuarioAtualizado.getAtivo(),
                    usuarioAtualizado.getDataCadastro()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/desativar")
    public ResponseEntity<UsuarioResponseDTO> desativarUsuario(@PathVariable UUID id){
        try{
            Usuario usuario = usuarioService.desativarUsuario(id);
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getAtivo(),
                    usuario.getDataCadastro()
            );
            return ResponseEntity.ok(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/ativar")
    public ResponseEntity<UsuarioResponseDTO> ativarUsuario(@PathVariable UUID id){
        try{
            Usuario usuario = usuarioService.ativarUsuario(id);
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getAtivo(),
                    usuario.getDataCadastro()
            );
            return ResponseEntity.ok(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}

