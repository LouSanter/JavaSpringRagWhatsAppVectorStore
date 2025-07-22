package com.lousanter.rag.Repository;

import com.lousanter.rag.Model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepo extends JpaRepository<Carrera, Long> {
}
