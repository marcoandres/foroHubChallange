package com.alura.foroHub.foroHubChallange.domain.respuesta;

import com.alura.foroHub.foroHubChallange.domain.topico.Topico;
import com.alura.foroHub.foroHubChallange.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "respuesta")
@Table(name = "Respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;

    private LocalDateTime fechaCreacion;
    private String autor;
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "autor_topico_id")
    private Usuario autorTopico;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Topico topico) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.autor = datosRegistroRespuesta.autor();
        this.topico = topico;
        this.autorTopico = topico.getAutor();
    }
}
