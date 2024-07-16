package com.alura.foroHub.foroHubChallange.domain.usuario;

public record UsuarioDTO(
        Long id,
        String nombre,
        String email
) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }

}
