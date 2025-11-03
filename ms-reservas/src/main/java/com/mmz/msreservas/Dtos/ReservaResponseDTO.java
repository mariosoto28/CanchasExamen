package com.mmz.msreservas.Dtos;


import com.mmz.msreservas.Entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDTO {
    private Long id;
    private Long usuarioId;
    private Long canchaId;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private BigDecimal duracionHoras;
    private BigDecimal precioTotal;
    private Reserva.EstadoReserva estado;
    private String metodoPago;
    private String notas;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}