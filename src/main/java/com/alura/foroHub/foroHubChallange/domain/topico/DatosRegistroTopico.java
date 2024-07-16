package com.alura.foroHub.foroHubChallange.domain.topico;

import com.alura.foroHub.foroHubChallange.domain.respuesta.DatosRegistroRespuesta;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        Status status,
        Long autorId,
        @NotBlank
        String autor,
        @NotBlank
        String curso,

        List<DatosRegistroRespuesta> respuestas
) {
}
