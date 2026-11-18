package com.transporte.conductores.dto;

import com.transporte.conductores.entities.Vehiculo;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class VehiculoResponse implements Serializable {
    private Integer id;
    private String nombre;
    private String creado;
    private int estatus;

    public VehiculoResponse(Vehiculo vehiculo) {
        this.id = vehiculo.getId();
        this.nombre = vehiculo.getNombre();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(vehiculo.getCreado().getTime()), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.creado = dateTime.format(formatter);
        this.estatus = vehiculo.getEstatus();
    }
}
