package com.msvcusuarios.api.controllers;

import com.msvcusuarios.api.models.requests.UsuarioRequest;
import com.msvcusuarios.api.models.responses.UsuarioResponse;
import com.msvcusuarios.infrastructure.services.contracts.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Slf4j
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAll(){
        log.info("<---- endpoint mostrar usuarios ---->");
        var usuarios = usuarioService.list();
        if (usuarios.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long id){
        log.info("<---- endpoint buscar usuario por id ---->");
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest request){
        log.info("<---- endpoint guardar usuario  ---->");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("<---- endpoint eliminar usuario  ---->");
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
