package com.msvccursos.infrastructure.services.impl;

import com.msvccursos.api.exceptions.customs.NotFoundException;
import com.msvccursos.api.models.requests.CursoRequest;
import com.msvccursos.api.models.requests.UsuarioRequest;
import com.msvccursos.api.models.responses.CursoResponse;
import com.msvccursos.api.models.responses.UsuarioResponse;
import com.msvccursos.clients.UsuarioClientRest;
import com.msvccursos.domain.entities.Curso;
import com.msvccursos.domain.entities.CursoUsuario;
import com.msvccursos.domain.models.Usuario;
import com.msvccursos.domain.repositories.CursoRepository;
import com.msvccursos.infrastructure.services.contracts.CursoService;
import com.msvccursos.utils.mappers.CursoMapper;
import com.msvccursos.utils.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {
    private final CursoRepository cursoRepository;
    private final UsuarioClientRest client;

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

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, long cursoId) {
        log.info("--> inicio servicio asignar usuario a curso");
        var curso = this.getOne(cursoId);
        try {
            var usuarioMSVC = client.getById(usuario.getId());
            var cursoUsuario = CursoUsuario.builder()
                    .usuarioId(usuarioMSVC.getId())
                    .build();
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMSVC);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Usuario crearUsuario(UsuarioRequest usuario, Long cursoId) {
        log.info("--> inicio servicio crear usuario y asignar a curso");
        var curso = this.getOne(cursoId);
        try {
            var usuarioMSVC = client.save(usuario);
            var cursoUsuario = CursoUsuario.builder()
                    .usuarioId(usuarioMSVC.getId())
                    .build();
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return usuarioMSVC;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public Usuario eliminarUsuario(Usuario usuario, Long cursoId) {
        log.info("--> inicio servicio eliminar usuario de un  curso");
        var curso = this.getOne(cursoId);
        try {
            var usuarioMSVC = client.getById(usuario.getId());
            var cursoUsuario = CursoUsuario.builder()
                    .usuarioId(usuarioMSVC.getId())
                    .build();
            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return usuarioMSVC;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Curso> porIdConUsuarios(Long id) {
        log.info("--> inicio servicio buscar un curso con susu usuarios");
        var curso = this.getOne(id);
        if (!curso.getCursoUsuarios().isEmpty()){
            List<Long> ids = curso.getCursoUsuarios().stream()
                    .map(CursoUsuario::getUsuarioId)
                    .toList();
            List<UsuarioResponse> users = client.usersForCourse(ids);
            curso.setUsuarios(users.stream().map(UsuarioMapper::mapToModel).toList());
        }
        return Optional.of(curso);
    }

    private Curso getOne(Long id){
        return cursoRepository.findById(id).orElseThrow(() ->{
            log.error("ERROR: curso no encontrado");
            return new  NotFoundException("curso no encontrado");
        });
    }
}
