package com.mmz.msreservas.Feign;

import com.mmz.msreservas.Dtos.UsuarioDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@FeignClient(name = "ms-usuario", path = "/usuarios")
public interface UsuarioFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "usuarioPorIdCB", fallbackMethod = "fallbackUsuarioPorId")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);

    // 🧩 Fallback correcto (mismo tipo de retorno + parámetro Throwable)
    default UsuarioDTO fallbackUsuarioPorId(Long id, Throwable e) {
        System.err.println("⚠️ CircuitBreaker: ms-usuario no disponible (ID: " + id + ")");
        return UsuarioDTO.builder()
                .id(0L)
                .email("desconocido@reserva.com")
                .nombre("Usuario")
                .apellido("Desconocido")
                .telefono("N/A")
                .fotoPerfil(null)
                .estado(UsuarioDTO.EstadoUsuario.INACTIVO)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
