package com.mmz.msusuarios.Dtos;

import com.mmz.msusuarios.Entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private String telefono;
    private String fotoPerfil;
    private Usuario.EstadoUsuario estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}