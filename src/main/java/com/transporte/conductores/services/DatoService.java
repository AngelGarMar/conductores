package com.transporte.conductores.services;

import com.transporte.conductores.dto.DatoRequest;
import com.transporte.conductores.models.ResponseService;

public interface DatoService {
    ResponseService createDato(DatoRequest request);
}
