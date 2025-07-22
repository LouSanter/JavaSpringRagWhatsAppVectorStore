package com.lousanter.rag.Repository;

import com.lousanter.rag.Model.HistorialAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialAcademicoRepo extends JpaRepository<HistorialAcademico, Long> {

    List<HistorialAcademico> findByEstudianteIdEstudiante(Long id);
}
