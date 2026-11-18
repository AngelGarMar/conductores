package com.transporte.conductores.dto;

import com.transporte.conductores.entities.Conductor;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class ConductorResponse implements Serializable {
    private Integer id;
    private String nombre;
    private String apaterno;
    private String amaterno;
    private Integer edad;
    private String celular;
    private Integer estatus;
    private String creado;
    private String modificado;
    private VehiculoResponse vehiculo;

    public ConductorResponse(Conductor conductor, VehiculoResponse vehiculo) {
        this.id = conductor.getId();
        this.nombre = conductor.getNombre();
        this.apaterno = conductor.getApaterno();
        this.amaterno = conductor.getAmaterno();
        this.edad = conductor.getEdad();
        this.celular = conductor.getCelular();
        this.estatus = conductor.getEstatus();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(conductor.getCreado().getTime()), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.creado = dateTime.format(formatter);
        if (conductor.getModificado() != null) {
            LocalDateTime dateModif = LocalDateTime.ofInstant(Instant.ofEpochMilli(conductor.getModificado().getTime()), ZoneId.systemDefault());
            this.modificado = dateModif.format(formatter);
        } else this.modificado = "";
        this.vehiculo = vehiculo;
    }
}
