package com.lousanter.rag.Repository;

import com.lousanter.rag.Model.Ciclo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CicloRepo extends JpaRepository<Ciclo, Long> {
}
