package com.mmz.msubicacion.Controller;


import com.mmz.msubicacion.Dtos.CanchaDTO;
import com.mmz.msubicacion.Dtos.UbicacionRequestDTO;
import com.mmz.msubicacion.Dtos.UbicacionResponseDTO;
import com.mmz.msubicacion.Dtos.UbicacionUpdateDTO;
import com.mmz.msubicacion.Service.UbicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ubicaciones")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionService ubicacionService;

    @PostMapping
    public ResponseEntity<UbicacionResponseDTO> crearUbicacion(@RequestBody UbicacionRequestDTO request) {
        UbicacionResponseDTO response = ubicacionService.crearUbicacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionResponseDTO> obtenerUbicacionPorId(@PathVariable Long id) {
        UbicacionResponseDTO response = ubicacionService.obtenerUbicacionPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancha/{canchaId}")
    public ResponseEntity<UbicacionResponseDTO> obtenerUbicacionPorCanchaId(@PathVariable Long canchaId) {
        UbicacionResponseDTO response = ubicacionService.obtenerUbicacionPorCanchaId(canchaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UbicacionResponseDTO>> obtenerTodasLasUbicaciones() {
        List<UbicacionResponseDTO> response = ubicacionService.obtenerTodasLasUbicaciones();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/distrito/{distrito}")
    public ResponseEntity<List<UbicacionResponseDTO>> obtenerUbicacionesPorDistrito(
            @PathVariable String distrito) {
        List<UbicacionResponseDTO> response = ubicacionService.obtenerUbicacionesPorDistrito(distrito);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<UbicacionResponseDTO>> obtenerUbicacionesPorCiudad(
            @PathVariable String ciudad) {
        List<UbicacionResponseDTO> response = ubicacionService.obtenerUbicacionesPorCiudad(ciudad);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<UbicacionResponseDTO>> obtenerUbicacionesPorDepartamento(
            @PathVariable String departamento) {
        List<UbicacionResponseDTO> response = ubicacionService.obtenerUbicacionesPorDepartamento(departamento);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UbicacionResponseDTO>> obtenerUbicacionesPorCiudadYDistrito(
            @RequestParam String ciudad,
            @RequestParam String distrito) {
        List<UbicacionResponseDTO> response = ubicacionService.obtenerUbicacionesPorCiudadYDistrito(
            ciudad, distrito);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cercanas")
    public ResponseEntity<List<CanchaDTO>> buscarCanchasCercanas(
            @RequestParam BigDecimal latitud,
            @RequestParam BigDecimal longitud,
            @RequestParam(defaultValue = "5.0") Double radiusKm) {
        List<CanchaDTO> response = ubicacionService.buscarCanchasCercanas(
            latitud, longitud, radiusKm);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionResponseDTO> actualizarUbicacion(
            @PathVariable Long id,
            @RequestBody UbicacionUpdateDTO updateDTO) {
        UbicacionResponseDTO response = ubicacionService.actualizarUbicacion(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        ubicacionService.eliminarUbicacion(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cancha/{canchaId}")
    public ResponseEntity<Void> eliminarUbicacionPorCanchaId(@PathVariable Long canchaId) {
        ubicacionService.eliminarUbicacionPorCanchaId(canchaId);
        return ResponseEntity.noContent().build();
    }
}