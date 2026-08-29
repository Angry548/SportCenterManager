package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.TipoMembresia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITipoMembresiaRepository extends JpaRepository<TipoMembresia, Integer> {

}
