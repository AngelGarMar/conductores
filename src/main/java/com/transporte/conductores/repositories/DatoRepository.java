package com.transporte.conductores.repositories;

import com.transporte.conductores.entities.Dato;
import com.transporte.conductores.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatoRepository extends JpaRepository<Dato, Integer> {
    Optional<Dato> findByVehiculoAndEstatus(Vehiculo vehiculo, Integer estatus);
}
