package com.transporte.conductores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConductorRequest implements Serializable {
    @NotBlank(message = "El campo nombre no debe estar vacío.")
    @Size(min = 1, max = 20)
    private String nombre;
    @Size(min = 0, max = 20)
    private String apaterno;
    @Size(min = 0, max = 20)
    private String amaterno;
    @NotNull(message = "El campo edad no debe estar vacío.")
    private Integer edad;
    @Pattern(regexp = "^$|^\\d{10}$", message = "El campo celular debe tener exactamente 10 números si se proporciona")
    private String celular;
    @NotNull(message = "El campo vehiculoId no debe estar vacío.")
    private Integer vehiculoId;
}
