package com.transporte.conductores.controllers;

import com.transporte.conductores.constants.Constants;
import com.transporte.conductores.dto.VehiculoRequest;
import com.transporte.conductores.models.ResponseService;
import com.transporte.conductores.services.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
public class VehiculoController {
    @Autowired
    private VehiculoService service;

    @PostMapping
    public ResponseEntity<ResponseService> createVehicle(@Valid @RequestBody VehiculoRequest request, BindingResult result){
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.createVehicle(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{idVehicle}")
    public ResponseEntity<ResponseService> getVehicle(@PathVariable Integer idVehicle) {
        ResponseService response = service.getVehicle(idVehicle);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseService> getAllVehicles() {
        ResponseService response = service.getAllVehicles();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{idVehicle}")
    public ResponseEntity<ResponseService> updateVehicle(@RequestBody @Valid VehiculoRequest request, BindingResult result, @PathVariable Integer idVehicle) {
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.updateVehicle(idVehicle, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<ResponseService> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return new ResponseEntity<>(new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.MISSED_INFORMATION, errors), HttpStatus.OK);
    }
}
