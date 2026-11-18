package com.transporte.conductores.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "conductores")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(min = 1, max = 20)
    private String nombre;
    @Size(min = 0, max = 20)
    private String apaterno;
    @Size(min = 0, max = 20)
    private String amaterno;
    @NotNull
    private Integer edad;
    @Size(min = 0, max = 10)
    private String celular;
    @NotNull
    private Integer estatus;
    private Timestamp creado;
    private Timestamp modificado;
    @OneToOne
    @JoinColumn(name = "vehiculos_id")
    private Vehiculo vehiculo;
}
