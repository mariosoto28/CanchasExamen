package com.mmz.mscanchas.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "canchas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "propietario_id", nullable = false)
    private UUID propietarioId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_deporte", nullable = false, length = 20)
    private TipoDeporte tipoDeporte;

    @Column(name = "tipo_superficie", length = 50)
    private String tipoSuperficie;

    @Column(nullable = false)
    @Builder.Default
    private Boolean techada = false;

    @Column(name = "capacidad_jugadores")
    private Integer capacidadJugadores;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoCancha estado = EstadoCancha.DISPONIBLE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
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
