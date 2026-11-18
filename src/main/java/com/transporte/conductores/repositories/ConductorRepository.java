package com.transporte.conductores.repositories;

import com.transporte.conductores.entities.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConductorRepository extends JpaRepository<Conductor, Integer> {
    Optional<Conductor> findByNombreAndApaternoAndAmaternoAndEstatus(String nombre, String apaterno, String amaterno, Integer estatus);
    Optional<Conductor> findByIdAndEstatus(Integer id, Integer estatus);
    List<Conductor> findByEstatus(Integer estatus);
    Optional<Conductor> findByNombreAndApaternoAndAmaternoAndEstatusAndIdNot(String nombre, String apaterno, String amaterno, Integer estatus, Integer id);
}
