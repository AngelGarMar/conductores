package com.transporte.conductores.services.impl;

import com.transporte.conductores.constants.Constants;
import com.transporte.conductores.dto.VehiculoRequest;
import com.transporte.conductores.dto.VehiculoResponse;
import com.transporte.conductores.entities.Vehiculo;
import com.transporte.conductores.models.ResponseService;
import com.transporte.conductores.repositories.VehiculoRepository;
import com.transporte.conductores.services.VehiculoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VehiculoServiceImpl implements VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Value("${app.maintenance.createVehicle}")
    private boolean createVehicle;

    @Override
    @Transactional
    public ResponseService createVehicle(VehiculoRequest request) {
        log.info("se revisa que por configuración se puedan crear vehículos");
        if (!createVehicle) {
            log.info("no se pueden crear vehículos");
            return new ResponseService(Constants.RESPONSE_TYPE_WARNING, Constants.VEHICLE_CREATE_DISABLED, null);
        }
        log.info("se revisa que no exista el mismo nombre");
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByNombreAndEstatus(request.getNombre(), 1);
        if (vehiculoOptional.isPresent()) {
            log.info("ya existe ese vehiculo");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.VEHICLE_EXISTS, null);
        }
        log.info("se comienza a guardar el vehículo");
        Vehiculo vehiculo = Vehiculo.builder()
                .nombre(request.getNombre().trim())
                .creado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))))
                .estatus(1)
                .build();
        VehiculoResponse vehiculoResponse = new VehiculoResponse(vehiculoRepository.save(vehiculo));
        log.info("se regresa el vehículo guardado");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.VEHICLE_SAVED, vehiculoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getVehicle(Integer idVehicle) {
        log.info("se revisa que exista el vehículo");
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByIdAndEstatus(idVehicle, 1);
        if (vehiculoOptional.isEmpty()) {
            log.info("no existe el vehículo");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.VEHICLE_NOT_EXISTS, null);
        }
        VehiculoResponse response = new VehiculoResponse(vehiculoOptional.get());
        log.info("se regresa el vehículo");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.VEHICLE_KNOWN, response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getAllVehicles() {
        log.info("se obtienen todos los vehículos");
        List<Vehiculo> vehiculos = vehiculoRepository.findByEstatus(1);
        log.info("vehículos encontrados: {}", vehiculos.size());
        List<VehiculoResponse> response = vehiculos.stream().map(VehiculoResponse::new).toList();
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.VEHICLES_OBTAINED, response);
    }

    @Override
    @Transactional
    public ResponseService updateVehicle(Integer idVehicle, VehiculoRequest request) {
        log.info("se revisa que exista el vehículo");
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByIdAndEstatus(idVehicle, 1);
        if (vehiculoOptional.isEmpty()) {
            log.info("no existe el vehículo");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.VEHICLE_NOT_EXISTS, null);
        }
        Vehiculo vehiculo = vehiculoOptional.get();
        log.info("se revisa que no exista el mismo nombre de vehículo");
        Optional<Vehiculo> vehiculoNombreOptional = vehiculoRepository.findByNombreAndEstatusAndIdNot(request.getNombre(), 1, idVehicle);
        if (vehiculoNombreOptional.isPresent()) {
            log.info("ya existe un vehículo con ese nombre");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.VEHICLE_EXISTS, null);
        }
        log.info("se comienza a actualizar el vehículo");
        vehiculo.setNombre(request.getNombre().trim());
        vehiculo.setModificado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))));
        VehiculoResponse response = new VehiculoResponse(vehiculoRepository.save(vehiculo));
        log.info("se regresa el vehículo actualizado");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.VEHICLE_UPDATED, response);
    }
}
