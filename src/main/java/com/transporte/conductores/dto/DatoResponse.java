package com.transporte.conductores.dto;

import com.transporte.conductores.entities.Dato;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class DatoResponse implements Serializable {
    private Integer id;
    private Integer asientos;
    private String placa;
    private String numeroEco;
    private String creado;
    private VehiculoResponse vehiculo;

    public DatoResponse(Dato dato, VehiculoResponse vehiculo) {
        this.id = dato.getId();
        this.asientos = dato.getAsientos();
        this.placa = dato.getPlaca();
        this.numeroEco = dato.getNumeroEco();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dato.getCreado().getTime()), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.creado = dateTime.format(formatter);
        this.vehiculo = vehiculo;
    }
}
