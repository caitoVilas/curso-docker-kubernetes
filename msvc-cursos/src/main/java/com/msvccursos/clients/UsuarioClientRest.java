package com.msvccursos.clients;

import com.msvccursos.api.models.requests.UsuarioRequest;
import com.msvccursos.domain.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001/api/v1/usuarios")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable Long id);

    @PostMapping
    public Usuario save(@RequestBody UsuarioRequest request);
}
