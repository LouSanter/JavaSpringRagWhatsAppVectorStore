package com.lousanter.rag.Repository;

import com.lousanter.rag.Model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepo extends JpaRepository<Estudiante, Long> {

    @Query("SELECT ha.estudiante FROM HistorialAcademico ha WHERE ha.ciclo.nombre = :nombre")
    List<Estudiante> findEstudiantesPorCiclo(@Param("nombre") String nombre);

}
