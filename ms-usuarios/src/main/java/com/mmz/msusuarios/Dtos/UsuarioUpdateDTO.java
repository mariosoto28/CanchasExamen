package com.mmz.msusuarios.Dtos;

import com.mmz.msusuarios.Entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {
    private String nombre;
    private String apellido;
    private String telefono;
    private String fotoPerfil;
    private Usuario.EstadoUsuario estado;
}
