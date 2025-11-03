package com.mmz.msreservas.Dtos;

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
public class CanchaDTO {
    private Long id;
    private UUID propietarioId;
    private String nombre;
    private String descripcion;
    private TipoDeporte tipoDeporte;
    private String tipoSuperficie;
    private Boolean techada;
    private Integer capacidadJugadores;
    private EstadoCancha estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum TipoDeporte {
        FUTBOL,
        BASQUET,
        TENIS,
        VOLEY,
        PADEL
    }

    public enum EstadoCancha {
        DISPONIBLE,
        MANTENIMIENTO,
        INACTIVA
    }
}
