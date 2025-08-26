
package com.estoqueBelissima.controle_estoque.repository;

import com.estoqueBelissima.controle_estoque.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(@NotBlank(message = "O email é obrigatório") @Email String email);

    Optional<Usuario> findByName(String name);
}

