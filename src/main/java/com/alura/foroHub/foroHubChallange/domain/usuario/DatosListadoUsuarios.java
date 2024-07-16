package com.alura.foroHub.foroHubChallange.domain.usuario;

public record DatosListadoUsuarios(

        Long id,
        String nombre
) {
    public DatosListadoUsuarios(Usuario usuario){
        this(usuario.getId(), usuario.getNombre());
    }
}
