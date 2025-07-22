package com.lousanter.rag.Repository;

import com.lousanter.rag.Model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {

    @Query("SELECT c FROM Curso c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Curso> buscarPorNombre(@Param("nombre") String nombre);

}
