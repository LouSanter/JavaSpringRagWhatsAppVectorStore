package com.lousanter.rag.Model.Repository;

import com.lousanter.rag.Model.Entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepo extends JpaRepository<Carrera, Long> {
}
