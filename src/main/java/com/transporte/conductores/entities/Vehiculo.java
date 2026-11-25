package com.transporte.conductores.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

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
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conductor> conductores;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vehiculo")
    private Dato dato;
}
