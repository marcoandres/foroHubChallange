package com.alura.foroHub.foroHubChallange.domain.topico;

import com.alura.foroHub.foroHubChallange.domain.respuesta.Respuesta;
import com.alura.foroHub.foroHubChallange.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "topico")
@Table(name = "Topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@JsonIgnoreProperties({"respuestas"})
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private String curso;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;


    public Topico(DatosRegistroTopico datosRegistroTopico, Usuario autor) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = datosRegistroTopico.status();
        this.autor = autor != null ? autor : new Usuario();
        this.curso = datosRegistroTopico.curso();
        this.respuestas = datosRegistroTopico.respuestas() != null
                ? datosRegistroTopico.respuestas().stream()
                .map(r -> new Respuesta(r, this))
                .collect(Collectors.toList())
                : new ArrayList<>();
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.findById(this.id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));
        this.titulo = datosActualizarTopico.titulo() != null
                ? datosActualizarTopico.titulo() : this.titulo;
        this.mensaje = datosActualizarTopico.mensaje() != null
                ? datosActualizarTopico.mensaje() : this.mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.status = datosActualizarTopico.status() != null
                ? datosActualizarTopico.status() : this.status;
        this.curso = datosActualizarTopico.curso() != null
                ? datosActualizarTopico.curso() : this.curso;
    }
}