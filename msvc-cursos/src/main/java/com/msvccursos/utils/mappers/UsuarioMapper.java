package com.msvccursos.utils.mappers;

import com.msvccursos.api.models.responses.UsuarioResponse;
import com.msvccursos.domain.models.Usuario;

public class UsuarioMapper {
    public static Usuario mapToModel(UsuarioResponse response){
        return Usuario.builder()
                .id(response.getId())
                .name(response.getName())
                .email(response.getEmail())
                .password(response.getPassword())
                .build();
    }

    public static  UsuarioResponse mapToDto(Usuario user){
        return UsuarioResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
