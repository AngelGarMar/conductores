package com.transporte.conductores.controllers;

import com.transporte.conductores.constants.Constants;
import com.transporte.conductores.dto.DatoRequest;
import com.transporte.conductores.models.ResponseService;
import com.transporte.conductores.services.DatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DatoController {
    @Autowired
    private DatoService service;

    @PostMapping
    public ResponseEntity<ResponseService> createData(@Valid @RequestBody DatoRequest dato, BindingResult result) {
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.createDato(dato);
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
