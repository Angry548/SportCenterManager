package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

}



