package com.alura.foroHub.foroHubChallange.domain.topico;

import lombok.Getter;

@Getter
public class DatosRespuestaTopico {
    private Long id;
    private String titulo;
    private String mensaje;
    private String autor;
    private String curso;

    public DatosRespuestaTopico(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensaje = topico.getMensaje();
        this.autor = topico.getAutor().getNombre();
        this.curso = topico.getCurso();
    }
}
