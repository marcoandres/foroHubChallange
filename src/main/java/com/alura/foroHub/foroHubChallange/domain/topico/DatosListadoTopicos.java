package com.alura.foroHub.foroHubChallange.domain.topico;

import com.alura.foroHub.foroHubChallange.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosListadoTopicos(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Status status,
        Usuario autor,
        String curso
) {
    public DatosListadoTopicos(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
    }
}
