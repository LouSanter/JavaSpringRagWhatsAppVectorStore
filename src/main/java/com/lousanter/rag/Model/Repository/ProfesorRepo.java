package com.lousanter.rag.Model.Repository;

import com.lousanter.rag.Model.Entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepo extends JpaRepository<Profesor, Long> {
}
