package com.transporte.conductores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class VehiculoRequest implements Serializable {
    @NotBlank(message = "El campo nombre no debe estar vacío.")
    @Size(min = 1, max = 45)
    private String nombre;
}
