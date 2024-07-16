package com.alura.foroHub.foroHubChallange.domain.topico;

import com.alura.foroHub.foroHubChallange.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Usuario> findUsuarioById(Long id);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
