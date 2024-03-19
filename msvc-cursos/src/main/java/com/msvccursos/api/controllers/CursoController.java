package com.msvccursos.api.controllers;

import com.msvccursos.api.models.requests.CursoRequest;
import com.msvccursos.api.models.requests.UsuarioRequest;
import com.msvccursos.api.models.responses.CursoResponse;
import com.msvccursos.domain.models.Usuario;
import com.msvccursos.infrastructure.services.contracts.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
@RequestMapping("/api/v1/cursos")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "MSVC - Cursos")
public class CursoController {
    private final CursoService cursoService;

    @GetMapping
    @Operation(summary = "servicio mostrar cursos", description = "servicio mostrar cursos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<List<CursoResponse>> list(){
        log.info("<-- endpoint mostrar cursos -->");
        var cursos = cursoService.list();
        if (cursos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "servicio buscar curso por id"
            , description = "servicio buscar curso por id")
    @Parameter(name = "id", description = "id del curso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<CursoResponse> getBYId(@PathVariable Long id){
        log.info("<-- endpoint buscar cursos por id -->");
        return ResponseEntity.ok(cursoService.getById(id));
    }

    @PostMapping
    @Operation(summary = "servicio guardar curso"
            , description = "servicio guardar curso")
    @Parameter(name = "request", description = "datos del curso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<CursoResponse> save(@RequestBody CursoRequest request){
        log.info("<-- endpoint guardar cursos -->");
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "servicio eliminar curso por id"
            , description = "servicio eliminar curso por id")
    @Parameter(name = "id", description = "id del curso")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("<-- endpoint eliminar cursos por id -->");
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    @Operation(summary = "servicio para asignar usuario a  cursos",
               description = "servicio para asignar usuario a  cursos")
    @Parameters({
            @Parameter(name = "cursoId", description = "id del curso"),
            @Parameter(name = "request", description = "datos del usuario")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<Usuario> asignarUsuario(@RequestBody Usuario request,
                                                  @PathVariable Long cursoId){
        log.info("<-- endpoint asignar usuario a un curso -->");
        return ResponseEntity.ok(cursoService.asignarUsuario(request, cursoId).get());
    }

    @PostMapping("/crear-y-asignar/{cursoId}")
    @Operation(summary = "servicio para crear y asignar usuario a  cursos",
            description = "servicio para crear y asignar usuario a  cursos")
    @Parameters({
            @Parameter(name = "cursoId", description = "id del curso"),
            @Parameter(name = "request", description = "datos del usuario")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<Usuario> saveAndAssign(@RequestBody UsuarioRequest request,
                                                 @PathVariable Long cursoId){
        log.info("<-- endpoint crear y  asignar usuario a un curso -->");
        return ResponseEntity.ok(cursoService.crearUsuario(request, cursoId));

    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    @Operation(summary = "servicio para eliminar usuario a cursos",
            description = "servicio para eliminar usuario a  cursos")
    @Parameters({
            @Parameter(name = "cursoId", description = "id del curso"),
            @Parameter(name = "request", description = "datos del usuario")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<Usuario> eliminarUsuario(@RequestBody Usuario request,
                                                   @PathVariable Long cursoId){
        log.info("<-- endpoint eliminar usuario a un curso -->");
        return ResponseEntity.ok(cursoService.eliminarUsuario(request, cursoId));
    }
}
