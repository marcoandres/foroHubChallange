package com.alura.foroHub.foroHubChallange.controller;

import com.alura.foroHub.foroHubChallange.domain.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {;
        Usuario usuario = new Usuario(
                datosRegistroUsuario.nombre(),
                datosRegistroUsuario.email(),
                datosRegistroUsuario.contrasena()
        );
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioGuardado.getId())
                .toUri();

        return ResponseEntity.created(location).body(new UsuarioDTO(usuarioGuardado));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return ResponseEntity.ok(new UsuarioDTO(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuarios>> listadoUsuarios(Pageable paginacion){
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosListadoUsuarios::new));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        usuario.actualizarDatos(datosActualizarUsuario);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }
}