package com.mmz.msubicacion.feign;

import com.mmz.msubicacion.Dtos.CanchaDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.UUID;

@FeignClient(name = "ms-cancha", path = "/canchas")
public interface CanchaFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "canchaPorIdCB", fallbackMethod = "fallbackCanchaPorId")
    CanchaDTO obtenerCanchaPorId(@PathVariable("id") Long id);

    // Fallback correcto: mismo tipo de retorno + Throwable
    default CanchaDTO fallbackCanchaPorId(Long id, Throwable e) {
        System.err.println("⚠️ CircuitBreaker: ms-canchas no disponible (ID: " + id + ")");
        // ❌ Devolvemos una cancha "inválida" a propósito
        return CanchaDTO.builder()
                .id(0L)
                .nombre("Desconocido")
                .descripcion("Servicio no disponible")
                .build();
    }
}