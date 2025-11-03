package com.mmz.msubicacion.Service;


import com.mmz.msubicacion.Dtos.CanchaDTO;
import com.mmz.msubicacion.Dtos.UbicacionRequestDTO;
import com.mmz.msubicacion.Dtos.UbicacionResponseDTO;
import com.mmz.msubicacion.Dtos.UbicacionUpdateDTO;
import com.mmz.msubicacion.Entity.Ubicacion;
import com.mmz.msubicacion.Exception.DuplicateResourceException;
import com.mmz.msubicacion.Exception.ResourceNotFoundException;
import com.mmz.msubicacion.Repository.UbicacionRepository;
import com.mmz.msubicacion.feign.CanchaFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final CanchaFeign canchaFeign; //Feign client inyectado


    @Transactional
    public UbicacionResponseDTO crearUbicacion(UbicacionRequestDTO request) {
        Long canchaId = request.getCanchaId();

        if (ubicacionRepository.existsByCanchaId(canchaId)) {
            throw new DuplicateResourceException(
                    "Ya existe una ubicación para la cancha con id: " + canchaId);
        }

        //  Validación remota usando Feign (con CircuitBreaker y fallback)
        CanchaDTO canchaDTO = canchaFeign.obtenerCanchaPorId(canchaId);

        if (canchaDTO == null || canchaDTO.getId() == null || canchaDTO.getId() == 0L) {
            throw new ResourceNotFoundException(
                    "No se pudo validar la existencia de la cancha con id: " + canchaId);
        }

        Ubicacion ubicacion = Ubicacion.builder()
                .canchaId(canchaId)
                .direccion(request.getDireccion())
                .distrito(request.getDistrito())
                .ciudad(request.getCiudad())
                .departamento(request.getDepartamento())
                .codigoPostal(request.getCodigoPostal())
                .latitud(request.getLatitud())
                .longitud(request.getLongitud())
                .referencias(request.getReferencias())
                .build();

        Ubicacion savedUbicacion = ubicacionRepository.save(ubicacion);
        return mapToResponseDTO(savedUbicacion);
    }

    // --- Métodos de lectura ---
    @Transactional(readOnly = true)
    public UbicacionResponseDTO obtenerUbicacionPorId(Long id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con id: " + id));
        return mapToResponseDTO(ubicacion);
    }

    @Transactional(readOnly = true)
    public UbicacionResponseDTO obtenerUbicacionPorCanchaId(Long canchaId) {
        Ubicacion ubicacion = ubicacionRepository.findByCanchaId(canchaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ubicación no encontrada para cancha con id: " + canchaId));
        return mapToResponseDTO(ubicacion);
    }

    @Transactional(readOnly = true)
    public List<UbicacionResponseDTO> obtenerTodasLasUbicaciones() {
        return ubicacionRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UbicacionResponseDTO> obtenerUbicacionesPorDistrito(String distrito) {
        return ubicacionRepository.findByDistrito(distrito).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UbicacionResponseDTO> obtenerUbicacionesPorCiudad(String ciudad) {
        return ubicacionRepository.findByCiudad(ciudad).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UbicacionResponseDTO> obtenerUbicacionesPorDepartamento(String departamento) {
        return ubicacionRepository.findByDepartamento(departamento).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UbicacionResponseDTO> obtenerUbicacionesPorCiudadYDistrito(String ciudad, String distrito) {
        return ubicacionRepository.findByCiudadAndDistrito(ciudad, distrito).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca canchas cercanas por radio usando coordenadas.
     */
    @Transactional(readOnly = true)
    public List<CanchaDTO> buscarCanchasCercanas(BigDecimal latitud, BigDecimal longitud, Double radiusKm) {
        return ubicacionRepository.findCanchasCercanas(latitud, longitud, radiusKm).stream()
                .map(u -> canchaFeign.obtenerCanchaPorId(u.getCanchaId())) //  Llamada real al ms-canchas
                .collect(Collectors.toList());
    }

    // --- Métodos de actualización y eliminación ---
    @Transactional
    public UbicacionResponseDTO actualizarUbicacion(Long id, UbicacionUpdateDTO updateDTO) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con id: " + id));

        if (updateDTO.getDireccion() != null) ubicacion.setDireccion(updateDTO.getDireccion());
        if (updateDTO.getDistrito() != null) ubicacion.setDistrito(updateDTO.getDistrito());
        if (updateDTO.getCiudad() != null) ubicacion.setCiudad(updateDTO.getCiudad());
        if (updateDTO.getDepartamento() != null) ubicacion.setDepartamento(updateDTO.getDepartamento());
        if (updateDTO.getCodigoPostal() != null) ubicacion.setCodigoPostal(updateDTO.getCodigoPostal());
        if (updateDTO.getLatitud() != null) ubicacion.setLatitud(updateDTO.getLatitud());
        if (updateDTO.getLongitud() != null) ubicacion.setLongitud(updateDTO.getLongitud());
        if (updateDTO.getReferencias() != null) ubicacion.setReferencias(updateDTO.getReferencias());

        return mapToResponseDTO(ubicacionRepository.save(ubicacion));
    }

    @Transactional
    public void eliminarUbicacion(Long id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ubicación no encontrada con id: " + id);
        }
        ubicacionRepository.deleteById(id);
    }

    @Transactional
    public void eliminarUbicacionPorCanchaId(Long canchaId) {
        Ubicacion ubicacion = ubicacionRepository.findByCanchaId(canchaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ubicación no encontrada para cancha con id: " + canchaId));
        ubicacionRepository.delete(ubicacion);
    }

    // --- Mapeo a DTO ---
    private UbicacionResponseDTO mapToResponseDTO(Ubicacion ubicacion) {
        return UbicacionResponseDTO.builder()
                .id(ubicacion.getId())
                .canchaId(ubicacion.getCanchaId())
                .direccion(ubicacion.getDireccion())
                .distrito(ubicacion.getDistrito())
                .ciudad(ubicacion.getCiudad())
                .departamento(ubicacion.getDepartamento())
                .codigoPostal(ubicacion.getCodigoPostal())
                .latitud(ubicacion.getLatitud())
                .longitud(ubicacion.getLongitud())
                .referencias(ubicacion.getReferencias())
                .createdAt(ubicacion.getCreatedAt())
                .updatedAt(ubicacion.getUpdatedAt())
                .build();
    }
}

