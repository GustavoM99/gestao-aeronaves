package com.sonda.gestaoaeronaves.repository;

import com.sonda.gestaoaeronaves.model.Aeronave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AeronaveRepository extends JpaRepository<Aeronave, Long> {

    @Query("SELECT a FROM Aeronave a WHERE " +
           "LOWER(a.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(a.marca) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Aeronave> findByTermoBusca(@Param("termo") String termo);

    Long countByVendido(Boolean vendido);

    List<Aeronave> findByCreatedAfter(LocalDateTime date);

    @Query("SELECT a.marca as marca, COUNT(a) as quantidade " +
           "FROM Aeronave a GROUP BY a.marca ORDER BY quantidade DESC")
    List<Object[]> countByMarca();

    @Query("SELECT (a.ano / 10) * 10 as decada, COUNT(a) as quantidade " +
           "FROM Aeronave a GROUP BY (a.ano / 10) * 10 ORDER BY decada DESC")
    List<Object[]> countByDecada();
}
