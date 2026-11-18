package com.transporte.conductores.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "vehiculos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(min = 1, max = 45)
    private String nombre;
    private Timestamp creado;
    private Timestamp modificado;
    private Integer estatus;
    @OneToOne(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Conductor conductor;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vehiculo")
    private Dato dato;
}
