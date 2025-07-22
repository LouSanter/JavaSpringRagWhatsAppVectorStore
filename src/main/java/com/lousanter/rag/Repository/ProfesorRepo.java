package com.lousanter.rag.Repository;

import com.lousanter.rag.Model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepo extends JpaRepository<Profesor, Long> {
}
