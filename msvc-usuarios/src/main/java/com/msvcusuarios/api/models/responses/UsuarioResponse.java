package com.msvcusuarios.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsuarioResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
}
