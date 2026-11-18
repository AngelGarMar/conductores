package com.transporte.conductores.services;

import com.transporte.conductores.dto.VehiculoRequest;
import com.transporte.conductores.models.ResponseService;

public interface VehiculoService {
    ResponseService createVehicle(VehiculoRequest request);
    ResponseService getVehicle(Integer idVehicle);
    ResponseService getAllVehicles();
    ResponseService updateVehicle(Integer idVehicle, VehiculoRequest request);
}
