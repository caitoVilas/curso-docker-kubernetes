package com.msvccursos.utils.mappers;

import com.msvccursos.api.models.requests.CursoRequest;
import com.msvccursos.api.models.responses.CursoResponse;
import com.msvccursos.domain.entities.Curso;

public class CursoMapper {

    public static Curso mapToEntity(CursoRequest request){
        return Curso.builder()
                .name(request.getName())
                .build();
    }

    public static CursoResponse mapToDto(Curso curso){
        return CursoResponse.builder()
                .id(curso.getId())
                .name(curso.getName())
                .usuarios(curso.getUsuarios())
                .build();
    }
}
