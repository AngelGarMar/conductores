package com.transporte.conductores.services;

import com.transporte.conductores.dto.ConductorRequest;
import com.transporte.conductores.models.ResponseService;

public interface ConductorService {
    ResponseService createDriver(ConductorRequest request);
    ResponseService getDriver(Integer driverId);
    ResponseService getAllDrivers();
    ResponseService deleteDriver(Integer driverId);
    ResponseService updateDriver(Integer driverId, ConductorRequest request);
}
