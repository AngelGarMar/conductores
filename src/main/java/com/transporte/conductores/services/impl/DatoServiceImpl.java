package com.transporte.conductores.services.impl;

import com.transporte.conductores.constants.Constants;
import com.transporte.conductores.dto.DatoRequest;
import com.transporte.conductores.dto.DatoResponse;
import com.transporte.conductores.dto.VehiculoResponse;
import com.transporte.conductores.entities.Dato;
import com.transporte.conductores.entities.Vehiculo;
import com.transporte.conductores.models.ResponseService;
import com.transporte.conductores.repositories.DatoRepository;
import com.transporte.conductores.repositories.VehiculoRepository;
import com.transporte.conductores.services.DatoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Slf4j
public class DatoServiceImpl implements DatoService {
    @Autowired
    private DatoRepository datoRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Value("${app.maintenance.createData}")
    private boolean createData;

    @Override
    @Transactional
    public ResponseService createDato(DatoRequest request) {
        log.info("se revisa que por configuración se puedan crear datos");
        if (!createData) {
            log.info("no se pueden crear datos");
            return new ResponseService(Constants.RESPONSE_TYPE_WARNING, Constants.DATA_CREATE_DISABLED, null);
        }
        log.info("se revisa que exista el vehículo");
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findByIdAndEstatus(request.getIdVehiculo(), 1);
        if (optionalVehiculo.isEmpty()) {
            log.info("no existe el vehículo");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.VEHICLE_UNKNOWN, null);
        }
        VehiculoResponse vehiculoResponse = new VehiculoResponse(optionalVehiculo.get());
        log.info("se revisa que no tenga datos este vehiculo");
        Optional<Dato> datoOptional = datoRepository.findByVehiculoAndEstatus(optionalVehiculo.get(), 1);
        if (datoOptional.isPresent()) {
            log.info("ese vehículo ya tiene datos guardados");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.VEHICLE_DATA_EXISTS, null);
        }
        log.info("se comienzan a guardar los datos del vehículo");
        Dato datos = Dato.builder()
                .asientos(request.getAsientos())
                .placa(request.getPlaca() == null ? "" : request.getPlaca().trim())
                .numeroEco(request.getNumeroEco() == null ? "" : request.getNumeroEco().trim())
                .creado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))))
                .estatus(1)
                .vehiculo(optionalVehiculo.get())
                .build();
        DatoResponse response = new DatoResponse(datoRepository.save(datos), vehiculoResponse);
        log.info("se regresan los datos del vehículo guardados");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.VEHICLE_DATA_SAVED, response);
    }
}
