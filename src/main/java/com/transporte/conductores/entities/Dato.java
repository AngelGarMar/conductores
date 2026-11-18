package com.transporte.conductores.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "datos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Integer asientos;
    @Size(min = 0, max = 10)
    private String placa;
    @Size(min = 0, max = 5)
    private String numeroEco;
    private Timestamp creado;
    private Integer estatus;
    @OneToOne
    @JoinColumn(name = "vehiculos_id")
    private Vehiculo vehiculo;
}
