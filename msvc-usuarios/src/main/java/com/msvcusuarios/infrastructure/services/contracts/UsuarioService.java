package com.msvcusuarios.infrastructure.services.contracts;

import com.msvcusuarios.api.models.requests.UsuarioRequest;
import com.msvcusuarios.api.models.responses.UsuarioResponse;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<UsuarioResponse> list();

    UsuarioResponse getById(Long id);

    UsuarioResponse save(UsuarioRequest request);

    void delete(Long id);
}