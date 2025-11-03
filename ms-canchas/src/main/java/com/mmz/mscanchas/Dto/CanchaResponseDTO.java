package com.mmz.mscanchas.Dto;

import com.mmz.mscanchas.Entity.Cancha;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanchaResponseDTO {
    private Long id;
    private UUID propietarioId;
    private String nombre;
    private String descripcion;
    private Cancha.TipoDeporte tipoDeporte;
    private String tipoSuperficie;
    private Boolean techada;
    private Integer capacidadJugadores;
    private Cancha.EstadoCancha estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
