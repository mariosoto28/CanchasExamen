package com.mmz.msreservas.Repository;


import com.mmz.msreservas.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    List<Reserva> findByUsuarioId(Long usuarioId);
    
    List<Reserva> findByCanchaId(Long canchaId);
    
    List<Reserva> findByEstado(Reserva.EstadoReserva estado);
    
    List<Reserva> findByFechaReserva(LocalDate fechaReserva);
    
    List<Reserva> findByCanchaIdAndFechaReserva(Long canchaId, LocalDate fechaReserva);
    
    List<Reserva> findByUsuarioIdAndEstado(Long usuarioId, Reserva.EstadoReserva estado);
    
    Optional<Reserva> findByCanchaIdAndFechaReservaAndHoraInicio(
        Long canchaId, LocalDate fechaReserva, LocalTime horaInicio);
    
    @Query("SELECT r FROM Reserva r WHERE r.canchaId = :canchaId " +
           "AND r.fechaReserva = :fecha " +
           "AND r.estado != 'CANCELADA' " +
           "AND ((r.horaInicio <= :horaInicio AND r.horaFin > :horaInicio) " +
           "OR (r.horaInicio < :horaFin AND r.horaFin >= :horaFin) " +
           "OR (r.horaInicio >= :horaInicio AND r.horaFin <= :horaFin))")
    List<Reserva> findReservasConflictivas(
        @Param("canchaId") Long canchaId,
        @Param("fecha") LocalDate fecha,
        @Param("horaInicio") LocalTime horaInicio,
        @Param("horaFin") LocalTime horaFin);
    
    @Query("SELECT r FROM Reserva r WHERE r.fechaReserva BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findReservasPorRangoFechas(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin);
}