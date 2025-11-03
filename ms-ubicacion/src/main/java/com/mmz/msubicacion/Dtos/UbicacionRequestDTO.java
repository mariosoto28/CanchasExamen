package com.mmz.msubicacion.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionRequestDTO {
    private Long canchaId;
    private String direccion;
    private String distrito;
    private String ciudad;
    private String departamento;
    private String codigoPostal;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String referencias;
}