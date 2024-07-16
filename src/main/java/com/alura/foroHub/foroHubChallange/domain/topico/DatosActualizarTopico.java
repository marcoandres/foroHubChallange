package com.alura.foroHub.foroHubChallange.domain.topico;

import com.alura.foroHub.foroHubChallange.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosActualizarTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Status status,
        Usuario autor,
        String curso
) {
}