package com.lousanter.rag.services;

import com.lousanter.rag.Model.Carrera;
import com.lousanter.rag.Repository.CarreraRepo;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarreraService {

    @Autowired private CarreraRepo carreraRepo;
    @Autowired private VectorStore vectorStore;

    public Carrera save(Carrera carrera) {
        eliminarVectorPorIdCarrera(carrera.getIdCarrera());
        Carrera guardada = carreraRepo.save(carrera);
        guardarVector(guardada);
        return guardada;
    }

    public void deleteById(Long idCarrera) {
        carreraRepo.deleteById(idCarrera);
        eliminarVectorPorIdCarrera(idCarrera);
    }

    public List<Carrera> findAll() {
        return carreraRepo.findAll();
    }

    public Carrera findById(Long id) {
        return carreraRepo.findById(id).orElse(null);
    }

    private void guardarVector(Carrera carrera) {
        String texto = String.format(
                "idCarrera: %s%nNombre: %s%nDuración: %d ciclos",
                carrera.getIdCarrera(),
                carrera.getNombre(),
                carrera.getDuracion()
        );

        Document doc = Document.builder()
                .id(UUID.randomUUID().toString())
                .text(texto)
                .metadata(Map.of(
                        "tipo", "carrera",
                        "idCarrera", carrera.getIdCarrera()
                ))
                .build();

        vectorStore.add(List.of(doc));
    }

    private void eliminarVectorPorIdCarrera(Long idCarrera) {
        if (idCarrera == null) return;

        Filter.Expression filter = new Filter.Expression(
                Filter.ExpressionType.AND,
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("idCarrera"), new Filter.Value(idCarrera)),
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("tipo"), new Filter.Value("carrera"))
        );

        vectorStore.delete(filter);
    }
}
