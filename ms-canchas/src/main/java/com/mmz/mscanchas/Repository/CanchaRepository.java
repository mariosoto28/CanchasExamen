package com.mmz.mscanchas.Repository;


import com.mmz.mscanchas.Entity.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha, Long> {
    
    List<Cancha> findByPropietarioId(UUID propietarioId);
    
    List<Cancha> findByTipoDeporte(Cancha.TipoDeporte tipoDeporte);
    
    List<Cancha> findByEstado(Cancha.EstadoCancha estado);
    
    List<Cancha> findByTechada(Boolean techada);
    
    List<Cancha> findByTipoDeporteAndEstado(Cancha.TipoDeporte tipoDeporte, Cancha.EstadoCancha estado);
}