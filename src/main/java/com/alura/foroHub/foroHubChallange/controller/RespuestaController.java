package com.alura.foroHub.foroHubChallange.controller;

import com.alura.foroHub.foroHubChallange.domain.respuesta.DatosRegistroRespuesta;
import com.alura.foroHub.foroHubChallange.domain.respuesta.Respuesta;
import com.alura.foroHub.foroHubChallange.domain.respuesta.RespuestaRepository;
import com.alura.foroHub.foroHubChallange.domain.topico.Topico;
import com.alura.foroHub.foroHubChallange.domain.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/topicos/{topicoId}/respuestas")
public class RespuestaController {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;

    public RespuestaController(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
    }

    @PostMapping
    public ResponseEntity<Respuesta> crearRespuesta(@PathVariable Long topicoId, @RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta) {
        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        Respuesta respuesta = new Respuesta(datosRegistroRespuesta, topico);
        Respuesta respuestaGuardada = respuestaRepository.save(respuesta);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaGuardada);
    }


}
