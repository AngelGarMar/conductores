package com.transporte.conductores.repositories;

import com.transporte.conductores.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findByIdAndEstatus(Integer id, Integer estatus);
    Optional<Vehiculo> findByNombreAndEstatus(String nombre, Integer estatus);
    List<Vehiculo> findByEstatus(Integer estatus);
    Optional<Vehiculo> findByNombreAndEstatusAndIdNot(String nombre, Integer estatus, Integer id);
}
