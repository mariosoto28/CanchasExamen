package com.mmz.mscanchas.Dto;

import com.mmz.mscanchas.Entity.Cancha;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanchaUpdateDTO {
    private String nombre;
    private String descripcion;
    private Cancha.TipoDeporte tipoDeporte;
    private String tipoSuperficie;
    private Boolean techada;
    private Integer capacidadJugadores;
    private Cancha.EstadoCancha estado;
}
