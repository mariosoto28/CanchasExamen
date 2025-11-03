package com.mmz.msreservas.Controller;


import com.mmz.msreservas.Dtos.ReservaRequestDTO;
import com.mmz.msreservas.Dtos.ReservaResponseDTO;
import com.mmz.msreservas.Dtos.ReservaUpdateDTO;
import com.mmz.msreservas.Entity.Reserva;
import com.mmz.msreservas.Service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO response = reservaService.crearReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReservaPorId(@PathVariable Long id) {
        ReservaResponseDTO response = reservaService.obtenerReservaPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodasLasReservas() {
        List<ReservaResponseDTO> response = reservaService.obtenerTodasLasReservas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorUsuario(
            @PathVariable Long usuarioId) {
        List<ReservaResponseDTO> response = reservaService.obtenerReservasPorUsuario(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancha/{canchaId}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorCancha(
            @PathVariable Long canchaId) {
        List<ReservaResponseDTO> response = reservaService.obtenerReservasPorCancha(canchaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorEstado(
            @PathVariable Reserva.EstadoReserva estado) {
        List<ReservaResponseDTO> response = reservaService.obtenerReservasPorEstado(estado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<ReservaResponseDTO> response = reservaService.obtenerReservasPorFecha(fecha);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancha/{canchaId}/fecha/{fecha}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorCanchaYFecha(
            @PathVariable Long canchaId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<ReservaResponseDTO> response = reservaService.obtenerReservasPorCanchaYFecha(canchaId, fecha);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<ReservaResponseDTO> response = reservaService.obtenerReservasPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(
            @PathVariable Long id,
            @RequestBody ReservaUpdateDTO updateDTO) {
        ReservaResponseDTO response = reservaService.actualizarReserva(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> cambiarEstadoReserva(
            @PathVariable Long id,
            @RequestParam Reserva.EstadoReserva estado) {
        ReservaResponseDTO response = reservaService.cambiarEstadoReserva(id, estado);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ReservaResponseDTO> confirmarReserva(@PathVariable Long id) {
        ReservaResponseDTO response = reservaService.confirmarReserva(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelarReserva(@PathVariable Long id) {
        ReservaResponseDTO response = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<ReservaResponseDTO> completarReserva(@PathVariable Long id) {
        ReservaResponseDTO response = reservaService.completarReserva(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
