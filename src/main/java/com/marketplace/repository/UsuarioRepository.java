package com.marketplace.repository;

import com.marketplace.model.Usuario;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    /** Busca já "trancando" a linha no banco até o fim da transação (evita gastar os mesmos pontos 2x). */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from Usuario u where u.id = :id")
    Optional<Usuario> buscarComLock(@Param("id") Long id);
}
