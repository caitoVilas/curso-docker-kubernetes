package com.msvccursos.infrastructure.services.contracts;

import com.msvccursos.api.models.requests.CursoRequest;
import com.msvccursos.api.models.responses.CursoResponse;

import java.util.List;

public interface CursoService {
    List<CursoResponse> list();
    CursoResponse getById(Long id);
    CursoResponse save(CursoRequest request);
    void delete(Long id);
}
