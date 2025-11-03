package com.mmz.msubicacion.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionResponseDTO {
    private Long id;
    private Long canchaId;
    private String direccion;
    private String distrito;
    private String ciudad;
    private String departamento;
    private String codigoPostal;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String referencias;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}