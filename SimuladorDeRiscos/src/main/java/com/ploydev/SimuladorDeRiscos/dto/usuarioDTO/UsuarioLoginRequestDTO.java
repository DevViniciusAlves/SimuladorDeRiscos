package com.ploydev.SimuladorDeRiscos.dto.usuarioDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioLoginRequestDTO {
    private String email;
    private String senha;
}

