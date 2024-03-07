package com.msvccursos.infrastructure.services.impl;

import com.msvccursos.api.exceptions.customs.NotFoundException;
import com.msvccursos.api.models.requests.CursoRequest;
import com.msvccursos.api.models.responses.CursoResponse;
import com.msvccursos.domain.entities.Curso;
import com.msvccursos.domain.repositories.CursoRepository;
import com.msvccursos.infrastructure.services.contracts.CursoService;
import com.msvccursos.utils.mappers.CursoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {
    private final CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> list() {
        log.info("---> inicio servicio mostrar cursos");
        var cursos = cursoRepository.findAll();
        return cursos.stream().map(CursoMapper::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse getById(Long id) {
        log.info("---> inicio servicio buscar cursos por id");
        var curso = this.getOne(id);
        return CursoMapper.mapToDto(curso);
    }

    @Override
    @Transactional
    public CursoResponse save(CursoRequest request) {
        log.info("---> inicio servicio guardar cursos");
        return CursoMapper.mapToDto(cursoRepository.save(CursoMapper.mapToEntity(request)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar cursos");
        this.getOne(id);
        cursoRepository.deleteById(id);
    }

    private Curso getOne(Long id){
        return cursoRepository.findById(id).orElseThrow(() ->{
            log.error("ERROR: curso no encontrado");
            return new  NotFoundException("curso no encontrado");
        });
    }
}
