package com.mmz.msusuarios.Dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    private String email;



    private String nombre;

    private String apellido;

    private String telefono;
    private String fotoPerfil;
}