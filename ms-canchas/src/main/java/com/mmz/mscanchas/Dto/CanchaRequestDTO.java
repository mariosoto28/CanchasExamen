package com.mmz.mscanchas.Dto;

import com.mmz.mscanchas.Entity.Cancha;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanchaRequestDTO {
    private UUID propietarioId;
    private String nombre;
    private String descripcion;
    private Cancha.TipoDeporte tipoDeporte;
    private String tipoSuperficie;
    private Boolean techada;
    private Integer capacidadJugadores;
}