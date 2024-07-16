package com.alura.foroHub.foroHubChallange.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroRespuesta(
        @NotBlank
        String mensaje,
        @NotBlank
        String autor
) {
}