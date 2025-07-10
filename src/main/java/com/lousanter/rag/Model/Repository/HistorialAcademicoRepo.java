package com.lousanter.rag.Model.Repository;

import com.lousanter.rag.Model.Entity.HistorialAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialAcademicoRepo extends JpaRepository<HistorialAcademico, Long> {
}
