package com.ploydev.SimuladorDeRiscos.service;

import com.ploydev.SimuladorDeRiscos.entity.Usuario;
import com.ploydev.SimuladorDeRiscos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) throws Exception {
        Usuario usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        Usuario usuarioPorCpf = usuarioRepository.findByCpf(usuario.getCpf());
        usuario.setAtivo(true);

        if (usuarioExistente != null) {
            throw new Exception("Email já existente");
        }

        if (usuarioPorCpf != null) {
            throw new Exception("CPF já cadastrado");
        }

        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new Exception("Senha deve ter ao menos 6 caracteres");
        }

        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario " + id + " não foi encotrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }


    public Usuario atualizarUsuario(UUID id, Usuario usuarioAtualizado) {
        Usuario atualizarUsuario = buscarPorId(id);
        atualizarUsuario.setNome(usuarioAtualizado.getNome());
        atualizarUsuario.setEmail(usuarioAtualizado.getEmail());
        atualizarUsuario.setSenha(usuarioAtualizado.getSenha());
        atualizarUsuario.setCpf(usuarioAtualizado.getCpf());
        atualizarUsuario.setDataAtualizacao(LocalDateTime.now());

        return usuarioRepository.save(atualizarUsuario);
    }

    public Usuario desativarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario" + id + "não foi encontrado"));

        usuario.setAtivo(false);
        usuario.setDataAtualizacao(LocalDateTime.now());


        return usuarioRepository.save(usuario);
    }
    public Usuario ativarUsuario(UUID id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuario" + id + "não foi encontrado"));

        usuario.setAtivo(true);
        usuario.setDataAtualizacao(LocalDateTime.now());

        return usuarioRepository.save(usuario);

    }

}