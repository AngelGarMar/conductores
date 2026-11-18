package com.transporte.conductores.services.impl;

import com.transporte.conductores.constants.Constants;
import com.transporte.conductores.dto.ConductorRequest;
import com.transporte.conductores.dto.ConductorResponse;
import com.transporte.conductores.dto.VehiculoResponse;
import com.transporte.conductores.entities.Conductor;
import com.transporte.conductores.entities.Vehiculo;
import com.transporte.conductores.models.ResponseService;
import com.transporte.conductores.repositories.ConductorRepository;
import com.transporte.conductores.repositories.VehiculoRepository;
import com.transporte.conductores.services.ConductorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ConductorServiceImpl implements ConductorService {
    @Autowired
    private ConductorRepository conductorRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Value("${app.maintenance.createDriver}")
    private boolean createDriver;

    @Override
    @Transactional
    public ResponseService createDriver(ConductorRequest request) {
        log.info("se revisa que por configuración se pueda crear el conductor");
        if (!createDriver) {
            log.info("no se puede crear al conductor");
            return new ResponseService(Constants.RESPONSE_TYPE_WARNING, Constants.DRIVER_CREATE_DISABLED, null);
        }
        log.info("se revisa que no exista el mismo nombre");
        Optional<Conductor> conductorOptional = conductorRepository.findByNombreAndApaternoAndAmaternoAndEstatus(request.getNombre(), request.getApaterno(),
                request.getAmaterno(), 1);
        if (conductorOptional.isPresent()) {
            log.info("ya existe un conductor con ese nombre");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.DRIVER_KNOWN, null);
        }
        log.info("se revisa que exista el vehiculoId");
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByIdAndEstatus(request.getVehiculoId(), 1);
        if (vehiculoOptional.isEmpty()) {
            log.info("no existe el vehiculoId");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.VEHICLE_NOT_EXISTS, null);
        }
        VehiculoResponse vehiculoResponse = new VehiculoResponse(vehiculoOptional.get());
        log.info("se comienza a guardar los datos del conductor");
        Conductor conductor = Conductor.builder()
                .nombre(request.getNombre().trim())
                .apaterno(request.getApaterno() == null ? "" : request.getApaterno().trim())
                .amaterno(request.getAmaterno() == null ? "" : request.getAmaterno().trim())
                .edad(request.getEdad())
                .celular(request.getCelular() == null ? "" : request.getCelular().trim())
                .estatus(1)
                .creado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))))
                .vehiculo(vehiculoOptional.get())
                .build();
        ConductorResponse response = new ConductorResponse(conductorRepository.save(conductor), vehiculoResponse);
        log.info("se regresa los datos del conductor guardado");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.DRIVER_SAVED, response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getDriver(Integer driverId) {
        log.info("se revisa si existe ese id de conductor y si está activo");
        Optional<Conductor> conductorOptional = conductorRepository.findByIdAndEstatus(driverId, 1);
        if (conductorOptional.isEmpty()) {
            log.info("no existe el conductor o está deshabilitado");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.DRIVER_UNKNOWN, null);
        }
        Conductor conductor = conductorOptional.get();
        log.info("se obtiene información del vehículo");
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByIdAndEstatus(conductor.getVehiculo().getId(), 1);
        VehiculoResponse vehiculoResponse = new VehiculoResponse(vehiculoOptional.get());
        ConductorResponse response = new ConductorResponse(conductor, vehiculoResponse);
        log.info("se regresan los datos del conductor");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.DRIVER_EXISTS, response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getAllDrivers() {
        log.info("obteniendo todos los conductores activos");
        List<Conductor> conductores = conductorRepository.findByEstatus(1);
        log.info("conductores encontrados: {}", conductores.size());
        List<ConductorResponse> response = new ArrayList<>();
        conductores.forEach(conductor -> {
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByIdAndEstatus(conductor.getVehiculo().getId(), 1);
            VehiculoResponse vehiculoResponse = new VehiculoResponse(vehiculoOptional.get());
            response.add(new ConductorResponse(conductor, vehiculoResponse));
        });
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.DRIVERS_RESPONSE, response);
    }

    @Override
    @Transactional
    public ResponseService deleteDriver(Integer driverId) {
        log.info("se revisa si existe ese id de conductor y si está activo");
        Optional<Conductor> conductorOptional = conductorRepository.findByIdAndEstatus(driverId, 1);
        if (conductorOptional.isEmpty()) {
            log.info("no existe el conductor o está deshabilitado");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.DRIVER_UNKNOWN, null);
        }
        Conductor conductor = conductorOptional.get();
        log.info("eliminando al conductor");
        conductor.setEstatus(0);
        conductor.setModificado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))));
        conductorRepository.save(conductor);
        log.info("conductor eliminado");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.DRIVER_ELIMINATED, null);
    }

    @Override
    @Transactional
    public ResponseService updateDriver(Integer driverId, ConductorRequest request) {
        log.info("se revisa si existe ese id de conductor y si está activo");
        Optional<Conductor> conductorOptional = conductorRepository.findByIdAndEstatus(driverId, 1);
        if (conductorOptional.isEmpty()) {
            log.info("no existe el conductor o está deshabilitado");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.DRIVER_UNKNOWN, null);
        }
        Conductor conductor = conductorOptional.get();
        log.info("se revisa que no exista el mismo nombre");
        Optional<Conductor> conductorOptionalName = conductorRepository.findByNombreAndApaternoAndAmaternoAndEstatusAndIdNot(request.getNombre(), request.getApaterno(),
                request.getAmaterno(), 1, driverId);
        if (conductorOptionalName.isPresent()) {
            log.info("ya existe un conductor con ese nombre");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.DRIVER_KNOWN, null);
        }
        log.info("se comienzan a actualizar los datos del conductor");
        conductor.setNombre(request.getNombre().trim());
        conductor.setApaterno(request.getApaterno() == null ? "" : request.getApaterno().trim());
        conductor.setAmaterno(request.getAmaterno() == null ? "" : request.getAmaterno().trim());
        conductor.setEdad(request.getEdad());
        conductor.setCelular(request.getCelular() == null ? "" : request.getCelular().trim());
        conductor.setModificado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))));
        VehiculoResponse vehiculoResponse = new VehiculoResponse(vehiculoRepository.findByIdAndEstatus(conductor.getVehiculo().getId(), 1).get());
        ConductorResponse response = new ConductorResponse(conductorRepository.save(conductor), vehiculoResponse);
        log.info("se regresa los datos del conductor actualizado");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.DRIVER_SAVED, response);
    }
}
