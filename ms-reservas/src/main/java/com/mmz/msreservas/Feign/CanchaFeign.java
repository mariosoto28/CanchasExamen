package com.mmz.msreservas.Feign;

import com.mmz.msreservas.Dtos.CanchaDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cancha", path = "/canchas")
public interface CanchaFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "canchaPorIdCB", fallbackMethod = "fallbackCanchaPorId")
    CanchaDTO obtenerCanchaPorId(@PathVariable("id") Long id);

    default CanchaDTO fallbackCanchaPorId(Long id, Throwable e) {
        System.err.println("⚠️ CircuitBreaker: ms-canchas no disponible (ID: " + id + ")");
        return CanchaDTO.builder()
                .id(0L)
                .nombre("Desconocido")
                .descripcion("Servicio no disponible temporalmente")
                .tipoDeporte(CanchaDTO.TipoDeporte.FUTBOL) // Valor por defecto
                .tipoSuperficie("N/A")
                .techada(false)
                .capacidadJugadores(0)
                .estado(CanchaDTO.EstadoCancha.INACTIVA)
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
