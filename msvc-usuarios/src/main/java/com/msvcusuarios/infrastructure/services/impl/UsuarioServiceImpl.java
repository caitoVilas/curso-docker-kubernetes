package com.msvcusuarios.infrastructure.services.impl;

import com.msvcusuarios.api.exceptions.customs.NotFoundException;
import com.msvcusuarios.api.models.requests.UsuarioRequest;
import com.msvcusuarios.api.models.responses.UsuarioResponse;
import com.msvcusuarios.domain.entities.Usuario;
import com.msvcusuarios.domain.repositories.UsuarioRepository;
import com.msvcusuarios.infrastructure.services.contracts.UsuarioService;
import com.msvcusuarios.utils.mappers.UsuarioMappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> list() {
        log.info("---> inicio servicio mostrar usuarios");
        var usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioMappers::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse getById(Long id) {
        log.info("---> inicio servicio buscar usuario por id");
        var user = this.findOne(id);
        return UsuarioMappers.mapToDto(user);
    }

    @Override
    @Transactional
    public UsuarioResponse save(UsuarioRequest request) {
        log.info("---> inicio servicio guardar usuario");
        var user = usuarioRepository.save(UsuarioMappers.maptoEntity(request));
        return UsuarioMappers.mapToDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("---> inicio servicio guardar usuario");
        this.findOne(id);
        usuarioRepository.deleteById(id);
    }

    private Usuario findOne(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> {
            log.error("ERROR: ".concat("usuario no encontrado"));
            return new NotFoundException("usuario no encontrado");
        });
    }
}
