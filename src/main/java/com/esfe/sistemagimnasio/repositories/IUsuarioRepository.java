package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.enums.Rol;
import com.esfe.sistemagimnasio.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository
        extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);


    @Query("""
            SELECT u
            FROM Usuario u
            WHERE u.rol = :rol
            AND NOT EXISTS (
                SELECT c.id
                FROM Cliente c
                WHERE c.usuario.id = u.id
            )
            """)
    List<Usuario> obtenerUsuariosDisponiblesCliente(
            @Param("rol") Rol rol
    );


    @Query("""
            SELECT u
            FROM Usuario u
            WHERE u.rol = :rol
            AND NOT EXISTS (
                SELECT e.id
                FROM Entrenador e
                WHERE e.usuario.id = u.id
            )
            """)
    List<Usuario> obtenerUsuariosDisponiblesEntrenador(
            @Param("rol") Rol rol
    );
}