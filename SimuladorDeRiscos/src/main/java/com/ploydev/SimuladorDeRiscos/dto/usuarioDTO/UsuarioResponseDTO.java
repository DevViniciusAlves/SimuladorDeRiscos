package com.ploydev.SimuladorDeRiscos.dto.usuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioResponseDTO {

    private UUID id;
    private String nome;
    private String email;
    private String cpf;
    private Boolean ativo = true;
    private LocalDateTime dataCadastro;

}
