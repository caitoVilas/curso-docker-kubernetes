package com.msvcusuarios.utils.mappers;

import com.msvcusuarios.api.models.requests.UsuarioRequest;
import com.msvcusuarios.api.models.responses.UsuarioResponse;
import com.msvcusuarios.domain.entities.Usuario;

public class UsuarioMappers {

    public static Usuario maptoEntity(UsuarioRequest request){
        return Usuario.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static UsuarioResponse mapToDto(Usuario usuario){
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .name(usuario.getName())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .build();
    }
}
