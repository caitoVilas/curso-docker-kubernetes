package com.msvccursos.api.models.responses;

import com.msvccursos.domain.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CursoResponse {
    private Long id;
    private String name;
    private List<Usuario> usuarios;
}
