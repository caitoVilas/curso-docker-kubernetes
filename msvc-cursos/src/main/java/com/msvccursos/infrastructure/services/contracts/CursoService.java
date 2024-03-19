package com.msvccursos.infrastructure.services.contracts;

import com.msvccursos.api.models.requests.CursoRequest;
import com.msvccursos.api.models.requests.UsuarioRequest;
import com.msvccursos.api.models.responses.CursoResponse;
import com.msvccursos.domain.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<CursoResponse> list();
    CursoResponse getById(Long id);
    CursoResponse save(CursoRequest request);
    void delete(Long id);
    Optional<Usuario> asignarUsuario(Usuario usuario, long cursoId);
    Usuario crearUsuario(UsuarioRequest usuario, Long cursoId);
    Usuario eliminarUsuario(Usuario usuario, Long cursoId);
}
