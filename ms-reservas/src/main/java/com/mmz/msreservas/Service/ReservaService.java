package com.mmz.msreservas.Service;


import com.mmz.msreservas.Dtos.*;
import com.mmz.msreservas.Entity.Reserva;
import com.mmz.msreservas.Exceptions.ReservaConflictException;
import com.mmz.msreservas.Exceptions.ResourceNotFoundException;
import com.mmz.msreservas.Feign.CanchaFeign;
import com.mmz.msreservas.Feign.UsuarioFeign;
import com.mmz.msreservas.Repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioFeign usuarioFeignClient;
    private final CanchaFeign canchaFeignClient;


    @Transactional
    public ReservaResponseDTO crearReserva(ReservaRequestDTO request) {

        UsuarioDTO usuario = usuarioFeignClient.obtenerUsuarioPorId(request.getUsuarioId());
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getUsuarioId());
        }
        if (usuario.getEstado() != UsuarioDTO.EstadoUsuario.ACTIVO) {
            throw new IllegalStateException("El usuario no está activo para realizar reservas");
        }

        CanchaDTO cancha = canchaFeignClient.obtenerCanchaPorId(request.getCanchaId());
        if (cancha == null) {
            throw new ResourceNotFoundException("Cancha no encontrada con ID: " + request.getCanchaId());
        }
        if (cancha.getEstado() != CanchaDTO.EstadoCancha.DISPONIBLE) {
            throw new IllegalStateException("La cancha no está disponible para reservas");
        }

        List<Reserva> conflictos = reservaRepository.findReservasConflictivas(
                request.getCanchaId(),
                request.getFechaReserva(),
                request.getHoraInicio(),
                request.getHoraFin()
        );
        if (!conflictos.isEmpty()) {
            throw new ReservaConflictException("Ya existe una reserva en ese horario para la cancha seleccionada");
        }

        BigDecimal duracionHoras = calcularDuracionHoras(
                request.getHoraInicio(),
                request.getHoraFin()
        );

        Reserva reserva = Reserva.builder()
                .usuarioId(request.getUsuarioId())
                .canchaId(request.getCanchaId())
                .fechaReserva(request.getFechaReserva())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .duracionHoras(duracionHoras)
                .precioTotal(request.getPrecioTotal())
                .estado(Reserva.EstadoReserva.PENDIENTE)
                .metodoPago(request.getMetodoPago())
                .notas(request.getNotas())
                .build();

        Reserva saved = reservaRepository.save(reserva);
        return mapToResponseDTO(saved);
    }


    @Transactional
    public ReservaResponseDTO obtenerReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        return mapToResponseDTO(reserva);
    }

    @Transactional
    public List<ReservaResponseDTO> obtenerTodasLasReservas() {
        return reservaRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReservaResponseDTO> obtenerReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReservaResponseDTO> obtenerReservasPorCancha(Long canchaId) {
        return reservaRepository.findByCanchaId(canchaId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReservaResponseDTO> obtenerReservasPorEstado(Reserva.EstadoReserva estado) {
        return reservaRepository.findByEstado(estado).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReservaResponseDTO> obtenerReservasPorFecha(LocalDate fecha) {
        return reservaRepository.findByFechaReserva(fecha).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReservaResponseDTO> obtenerReservasPorCanchaYFecha(Long canchaId, LocalDate fecha) {
        return reservaRepository.findByCanchaIdAndFechaReserva(canchaId, fecha).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReservaResponseDTO> obtenerReservasPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return reservaRepository.findReservasPorRangoFechas(inicio, fin).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public ReservaResponseDTO actualizarReserva(Long id, ReservaUpdateDTO updateDTO) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

        if (updateDTO.getFechaReserva() != null) reserva.setFechaReserva(updateDTO.getFechaReserva());
        if (updateDTO.getHoraInicio() != null) reserva.setHoraInicio(updateDTO.getHoraInicio());
        if (updateDTO.getHoraFin() != null) reserva.setHoraFin(updateDTO.getHoraFin());

        if (updateDTO.getHoraInicio() != null || updateDTO.getHoraFin() != null) {
            BigDecimal duracionHoras = calcularDuracionHoras(reserva.getHoraInicio(), reserva.getHoraFin());
            reserva.setDuracionHoras(duracionHoras);
        }

        if (updateDTO.getPrecioTotal() != null) reserva.setPrecioTotal(updateDTO.getPrecioTotal());
        if (updateDTO.getEstado() != null) reserva.setEstado(updateDTO.getEstado());
        if (updateDTO.getMetodoPago() != null) reserva.setMetodoPago(updateDTO.getMetodoPago());
        if (updateDTO.getNotas() != null) reserva.setNotas(updateDTO.getNotas());

        Reserva updated = reservaRepository.save(reserva);
        return mapToResponseDTO(updated);
    }

    @Transactional
    public ReservaResponseDTO cambiarEstadoReserva(Long id, Reserva.EstadoReserva nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

        reserva.setEstado(nuevoEstado);
        Reserva updated = reservaRepository.save(reserva);
        return mapToResponseDTO(updated);
    }

    @Transactional
    public ReservaResponseDTO confirmarReserva(Long id) {
        return cambiarEstadoReserva(id, Reserva.EstadoReserva.CONFIRMADA);
    }

    @Transactional
    public ReservaResponseDTO cancelarReserva(Long id) {
        return cambiarEstadoReserva(id, Reserva.EstadoReserva.CANCELADA);
    }

    @Transactional
    public ReservaResponseDTO completarReserva(Long id) {
        return cambiarEstadoReserva(id, Reserva.EstadoReserva.COMPLETADA);
    }

    @Transactional
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con id: " + id);
        }
        reservaRepository.deleteById(id);
    }

    private BigDecimal calcularDuracionHoras(LocalTime inicio, LocalTime fin) {
        long minutos = ChronoUnit.MINUTES.between(inicio, fin);
        return BigDecimal.valueOf(minutos)
                .divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP);
    }

    private ReservaResponseDTO mapToResponseDTO(Reserva reserva) {
        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .usuarioId(reserva.getUsuarioId())
                .canchaId(reserva.getCanchaId())
                .fechaReserva(reserva.getFechaReserva())
                .horaInicio(reserva.getHoraInicio())
                .horaFin(reserva.getHoraFin())
                .duracionHoras(reserva.getDuracionHoras())
                .precioTotal(reserva.getPrecioTotal())
                .estado(reserva.getEstado())
                .metodoPago(reserva.getMetodoPago())
                .notas(reserva.getNotas())
                .createdAt(reserva.getCreatedAt())
                .updatedAt(reserva.getUpdatedAt())
                .build();
    }
}