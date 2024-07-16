package com.alura.foroHub.foroHubChallange.controller;

import com.alura.foroHub.foroHubChallange.domain.topico.*;
import com.alura.foroHub.foroHubChallange.domain.usuario.Usuario;
import com.alura.foroHub.foroHubChallange.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public TopicoController(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@Valid @RequestBody DatosRegistroTopico datosRegistroTopico) {
        Usuario usuario = usuarioRepository.findById(datosRegistroTopico.autorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un tópico con el mismo título y mensaje");
        }

        Topico topico = new Topico(datosRegistroTopico, usuario);
        Topico topicoGuardado = topicoRepository.save(topico);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(topicoGuardado.getId())
                .toUri();

        return ResponseEntity.created(location).body(new DatosRespuestaTopico(topicoGuardado));
    }
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listadoTopicos(@PageableDefault(size=10,sort="titulo") Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopicos::new)) ;
    }



    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalleTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));
        topico.actualizarDatos(datosActualizarTopico, topicoRepository);
        topicoRepository.save(topico);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }







}
