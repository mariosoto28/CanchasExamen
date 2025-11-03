package com.mmz.msubicacion.Repository;


import com.mmz.msubicacion.Entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    
    Optional<Ubicacion> findByCanchaId(Long canchaId);
    
    List<Ubicacion> findByDistrito(String distrito);
    
    List<Ubicacion> findByCiudad(String ciudad);
    
    List<Ubicacion> findByDepartamento(String departamento);
    
    List<Ubicacion> findByCiudadAndDistrito(String ciudad, String distrito);
    
    boolean existsByCanchaId(Long canchaId);
    
    // Query para buscar canchas cercanas usando fórmula de Haversine
    @Query("SELECT u FROM Ubicacion u WHERE " +
           "u.latitud IS NOT NULL AND u.longitud IS NOT NULL AND " +
           "(6371 * acos(cos(radians(:lat)) * cos(radians(u.latitud)) * " +
           "cos(radians(u.longitud) - radians(:lng)) + " +
           "sin(radians(:lat)) * sin(radians(u.latitud)))) <= :radiusKm " +
           "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(u.latitud)) * " +
           "cos(radians(u.longitud) - radians(:lng)) + " +
           "sin(radians(:lat)) * sin(radians(u.latitud))))")
    List<Ubicacion> findCanchasCercanas(
        @Param("lat") BigDecimal lat,
        @Param("lng") BigDecimal lng,
        @Param("radiusKm") Double radiusKm);
}
