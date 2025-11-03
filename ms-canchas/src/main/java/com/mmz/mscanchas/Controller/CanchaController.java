package com.mmz.mscanchas.Controller;

import com.mmz.mscanchas.Dto.CanchaRequestDTO;
import com.mmz.mscanchas.Dto.CanchaResponseDTO;
import com.mmz.mscanchas.Dto.CanchaUpdateDTO;
import com.mmz.mscanchas.Entity.Cancha;
import com.mmz.mscanchas.Service.CanchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/canchas")
@RequiredArgsConstructor
public class CanchaController {

    private final CanchaService canchaService;

    @PostMapping
    public ResponseEntity<CanchaResponseDTO> crearCancha(@RequestBody CanchaRequestDTO request) {
        CanchaResponseDTO response = canchaService.crearCancha(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanchaResponseDTO> obtenerCanchaPorId(@PathVariable Long id) {
        CanchaResponseDTO response = canchaService.obtenerCanchaPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CanchaResponseDTO>> obtenerTodasLasCanchas() {
        List<CanchaResponseDTO> response = canchaService.obtenerTodasLasCanchas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/propietario/{propietarioId}")
    public ResponseEntity<List<CanchaResponseDTO>> obtenerCanchasPorPropietario(
            @PathVariable UUID propietarioId) {
        List<CanchaResponseDTO> response = canchaService.obtenerCanchasPorPropietario(propietarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo-deporte/{tipoDeporte}")
    public ResponseEntity<List<CanchaResponseDTO>> obtenerCanchasPorTipoDeporte(
            @PathVariable Cancha.TipoDeporte tipoDeporte) {
        List<CanchaResponseDTO> response = canchaService.obtenerCanchasPorTipoDeporte(tipoDeporte);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CanchaResponseDTO>> obtenerCanchasPorEstado(
            @PathVariable Cancha.EstadoCancha estado) {
        List<CanchaResponseDTO> response = canchaService.obtenerCanchasPorEstado(estado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/techadas/{techada}")
    public ResponseEntity<List<CanchaResponseDTO>> obtenerCanchasTechadas(
            @PathVariable Boolean techada) {
        List<CanchaResponseDTO> response = canchaService.obtenerCanchasTechadas(techada);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CanchaResponseDTO>> buscarCanchas(
            @RequestParam Cancha.TipoDeporte tipoDeporte,
            @RequestParam Cancha.EstadoCancha estado) {
        List<CanchaResponseDTO> response = canchaService.buscarCanchas(tipoDeporte, estado);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanchaResponseDTO> actualizarCancha(
            @PathVariable Long id,
            @RequestBody CanchaUpdateDTO updateDTO) {
        CanchaResponseDTO response = canchaService.actualizarCancha(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CanchaResponseDTO> cambiarEstadoCancha(
            @PathVariable Long id,
            @RequestParam Cancha.EstadoCancha estado) {
        CanchaResponseDTO response = canchaService.cambiarEstadoCancha(id, estado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCancha(@PathVariable Long id) {
        canchaService.eliminarCancha(id);
        return ResponseEntity.noContent().build();
    }
}