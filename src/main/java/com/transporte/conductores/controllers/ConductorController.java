package com.transporte.conductores.controllers;

import com.transporte.conductores.constants.Constants;
import com.transporte.conductores.dto.ConductorRequest;
import com.transporte.conductores.models.ResponseService;
import com.transporte.conductores.services.ConductorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/drivers")
public class ConductorController {
    @Autowired
    private ConductorService service;

    @PostMapping
    public ResponseEntity<ResponseService> createDriver(@RequestBody @Valid ConductorRequest request, BindingResult result) {
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.createDriver(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<ResponseService> getDriver(@PathVariable Integer driverId) {
        ResponseService response = service.getDriver(driverId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ResponseService> getAllDrivers() {
        ResponseService response = service.getAllDrivers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<ResponseService> deleteDriver(@PathVariable Integer driverId) {
        ResponseService response = service.deleteDriver(driverId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<ResponseService> updateDriver(@RequestBody @Valid ConductorRequest request, BindingResult result, @PathVariable Integer driverId) {
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.updateDriver(driverId, request);
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
