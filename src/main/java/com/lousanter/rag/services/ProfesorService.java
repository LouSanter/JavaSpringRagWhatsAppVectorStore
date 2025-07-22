package com.lousanter.rag.services;


import com.lousanter.rag.Model.Profesor;
import com.lousanter.rag.Repository.ProfesorRepo;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepo profesorRepo;

    @Autowired
    private VectorStore vectorStore;

    public Profesor save(Profesor profesor) {
        // Primero eliminar si ya existía
        eliminarVectorPorIdProfesor(profesor.getIdProfesor());

        Profesor guardado = profesorRepo.save(profesor);

        guardarVector(guardado);

        return guardado;
    }

    public void deleteById(Long idProfesor) {
        eliminarVectorPorIdProfesor(idProfesor);
        profesorRepo.deleteById(idProfesor);
    }

    private void guardarVector(Profesor profesor) {
        String contenido = String.format("""
                Profesor:
                ID: %s
                Nombre completo: %s
                Email: %s
                Especialidad: %s
                """,
                profesor.getIdProfesor(),
                profesor.getNombreCompleto(),
                profesor.getEmail(),
                profesor.getEspecialidad()
        );

        Document doc = Document.builder()
                .id(UUID.randomUUID().toString())
                .text(contenido)
                .metadata(Map.of(
                        "tipo", "profesor",
                        "idProfesor", profesor.getIdProfesor()
                ))
                .build();

        vectorStore.add(List.of(doc));
    }

    private void eliminarVectorPorIdProfesor(Long idProfesor) {
        if (idProfesor == null) return;

        Filter.Expression expr = new Filter.Expression(
                Filter.ExpressionType.AND,
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("idProfesor"), new Filter.Value(idProfesor)),
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("tipo"), new Filter.Value("profesor"))
        );

        vectorStore.delete(expr);
    }
}
