package com.lousanter.rag.services;

import com.lousanter.rag.Model.Entity.Ciclo;
import com.lousanter.rag.Model.Repository.CicloRepo;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CicloService {

    @Autowired private CicloRepo cicloRepo;
    @Autowired private VectorStore vectorStore;

    public Ciclo save(Ciclo ciclo) {

        eliminarVectorPorIdCiclo(ciclo);

        // Guardar en BD
        Ciclo cicloGuardado = cicloRepo.save(ciclo);

        // Añadir al vector store
        guardarVector(cicloGuardado);

        return cicloGuardado;
    }



    public List<Ciclo> findAll(){
        return cicloRepo.findAll();
    }

    public Optional<Ciclo> findById(Long id){
        return cicloRepo.findById(id);
    }

    public void deleteById(Long idCiclo) {
        Optional<Ciclo> cicloVector = cicloRepo.findById(idCiclo);
        eliminarVectorPorIdCiclo(cicloVector.orElse(null));
        cicloRepo.deleteById(idCiclo);
    }



    private void guardarVector(Ciclo ciclo) {
        String contenido = String.format(
                "idCiclo: %s%nnombre: %s%nfechaInicio: %s%nfechaFin: %s",
                ciclo.getIdCiclo(), ciclo.getNombre(),
                ciclo.getFechaInicio(), ciclo.getFechaFin()
        );

        Document doc = Document
                .builder()
                .id(UUID.randomUUID().toString())
                .text(contenido)
                .metadata(Map.of(
                        "tipo", "ciclo",
                        "idCiclo", ciclo.getIdCiclo()
                ))
                .build();

        vectorStore.add(List.of(doc));
    }

    private void eliminarVectorPorIdCiclo(Ciclo ciclo) {
        if (ciclo !=null){
            Filter.Expression expr = new Filter.Expression(
                    Filter.ExpressionType.AND,
                    new Filter.Expression(
                            Filter.ExpressionType.EQ,
                            new Filter.Key("idCiclo"),
                            new Filter.Value(ciclo.getIdCiclo())
                    ),
                    new Filter.Expression(
                            Filter.ExpressionType.EQ,
                            new Filter.Key("tipo"),
                            new Filter.Value("ciclo")
                    )
            );
            vectorStore.delete(expr);
        }


    }
}
