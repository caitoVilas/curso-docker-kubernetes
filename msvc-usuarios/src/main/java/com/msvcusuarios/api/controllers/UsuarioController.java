package com.msvcusuarios.api.controllers;

import com.msvcusuarios.api.models.requests.UsuarioRequest;
import com.msvcusuarios.api.models.responses.UsuarioResponse;
import com.msvcusuarios.infrastructure.services.contracts.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "MSVC - Usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "servicio mostrar usuarios", description = "servicio mostrar usuarios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<List<UsuarioResponse>> getAll(){
        log.info("<---- endpoint mostrar usuarios ---->");
        var usuarios = usuarioService.list();
        if (usuarios.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "servicio buscar usuario por id"
              , description = "servicio buscar usuario por id")
    @Parameter(name = "id", description = "id del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long id){
        log.info("<---- endpoint buscar usuario por id ---->");
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @PostMapping
    @Operation(summary = "servicio guardar usuario"
            , description = "servicio guardar usuario")
    @Parameter(name = "request", description = "datos del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest request){
        log.info("<---- endpoint guardar usuario  ---->");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio eliminar usuario por id"
            , description = "servicio eliminar usuario por id")
    @Parameter(name = "id", description = "id del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("<---- endpoint eliminar usuario  ---->");
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios-por-curso")
    @Operation(summary = "servicio buscar usuario por curso"
            , description = "servicio buscar usuario por curso")
    @Parameter(name = "ids", description = "id del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<List<UsuarioResponse>> usersForCourse(@RequestParam List<Long> ids){
        log.info("<---- endpoint listar usuarios por curso  ---->");
        var responses = usuarioService.ListarPorId(ids);
        if (responses.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(responses);
    }
}
