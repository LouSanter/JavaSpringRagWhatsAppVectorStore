package com.lousanter.rag.Model.Repository;

import com.lousanter.rag.Model.Entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {
}
