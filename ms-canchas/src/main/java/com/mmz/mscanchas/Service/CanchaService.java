package com.mmz.mscanchas.Service;


import com.mmz.mscanchas.Dto.CanchaRequestDTO;
import com.mmz.mscanchas.Dto.CanchaResponseDTO;
import com.mmz.mscanchas.Dto.CanchaUpdateDTO;
import com.mmz.mscanchas.Exception.ResourceNotFoundException;
import com.mmz.mscanchas.Entity.Cancha;
import com.mmz.mscanchas.Repository.CanchaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CanchaService {

    private final CanchaRepository canchaRepository;

    @Transactional
    public CanchaResponseDTO crearCancha(CanchaRequestDTO request) {
        Cancha cancha = Cancha.builder()
                .propietarioId(request.getPropietarioId())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .tipoDeporte(request.getTipoDeporte())
                .tipoSuperficie(request.getTipoSuperficie())
                .techada(request.getTechada() != null ? request.getTechada() : false)
                .capacidadJugadores(request.getCapacidadJugadores())
                .estado(Cancha.EstadoCancha.DISPONIBLE)
                .build();

        Cancha savedCancha = canchaRepository.save(cancha);
        return mapToResponseDTO(savedCancha);
    }

    @Transactional(readOnly = true)
    public CanchaResponseDTO obtenerCanchaPorId(Long id) {
        Cancha cancha = canchaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancha no encontrada con id: " + id));
        return mapToResponseDTO(cancha);
    }

    @Transactional(readOnly = true)
    public List<CanchaResponseDTO> obtenerTodasLasCanchas() {
        return canchaRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CanchaResponseDTO> obtenerCanchasPorPropietario(UUID propietarioId) {
        return canchaRepository.findByPropietarioId(propietarioId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CanchaResponseDTO> obtenerCanchasPorTipoDeporte(Cancha.TipoDeporte tipoDeporte) {
        return canchaRepository.findByTipoDeporte(tipoDeporte).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CanchaResponseDTO> obtenerCanchasPorEstado(Cancha.EstadoCancha estado) {
        return canchaRepository.findByEstado(estado).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CanchaResponseDTO> obtenerCanchasTechadas(Boolean techada) {
        return canchaRepository.findByTechada(techada).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CanchaResponseDTO> buscarCanchas(Cancha.TipoDeporte tipoDeporte, Cancha.EstadoCancha estado) {
        return canchaRepository.findByTipoDeporteAndEstado(tipoDeporte, estado).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CanchaResponseDTO actualizarCancha(Long id, CanchaUpdateDTO updateDTO) {
        Cancha cancha = canchaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancha no encontrada con id: " + id));

        if (updateDTO.getNombre() != null) {
            cancha.setNombre(updateDTO.getNombre());
        }
        if (updateDTO.getDescripcion() != null) {
            cancha.setDescripcion(updateDTO.getDescripcion());
        }
        if (updateDTO.getTipoDeporte() != null) {
            cancha.setTipoDeporte(updateDTO.getTipoDeporte());
        }
        if (updateDTO.getTipoSuperficie() != null) {
            cancha.setTipoSuperficie(updateDTO.getTipoSuperficie());
        }
        if (updateDTO.getTechada() != null) {
            cancha.setTechada(updateDTO.getTechada());
        }
        if (updateDTO.getCapacidadJugadores() != null) {
            cancha.setCapacidadJugadores(updateDTO.getCapacidadJugadores());
        }
        if (updateDTO.getEstado() != null) {
            cancha.setEstado(updateDTO.getEstado());
        }

        Cancha updatedCancha = canchaRepository.save(cancha);
        return mapToResponseDTO(updatedCancha);
    }

    @Transactional
    public void eliminarCancha(Long id) {
        if (!canchaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cancha no encontrada con id: " + id);
        }
        canchaRepository.deleteById(id);
    }

    @Transactional
    public CanchaResponseDTO cambiarEstadoCancha(Long id, Cancha.EstadoCancha nuevoEstado) {
        Cancha cancha = canchaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancha no encontrada con id: " + id));
        
        cancha.setEstado(nuevoEstado);
        Cancha updatedCancha = canchaRepository.save(cancha);
        return mapToResponseDTO(updatedCancha);
    }

    private CanchaResponseDTO mapToResponseDTO(Cancha cancha) {
        return CanchaResponseDTO.builder()
                .id(cancha.getId())
                .propietarioId(cancha.getPropietarioId())
                .nombre(cancha.getNombre())
                .descripcion(cancha.getDescripcion())
                .tipoDeporte(cancha.getTipoDeporte())
                .tipoSuperficie(cancha.getTipoSuperficie())
                .techada(cancha.getTechada())
                .capacidadJugadores(cancha.getCapacidadJugadores())
                .estado(cancha.getEstado())
                .createdAt(cancha.getCreatedAt())
                .updatedAt(cancha.getUpdatedAt())
                .build();
    }
}
