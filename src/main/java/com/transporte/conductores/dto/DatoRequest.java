package com.transporte.conductores.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class DatoRequest implements Serializable {
    @NotNull(message = "El campo asientos no debe estar vacío.")
    private Integer asientos;
    @Size(min = 0, max = 10)
    private String placa;
    @Size(min = 0, max = 5)
    private String numeroEco;
    @NotNull(message = "El campo vehiculo no debe estar vacío.")
    private Integer idVehiculo;
}
