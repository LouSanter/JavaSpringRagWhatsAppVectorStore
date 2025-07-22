package com.lousanter.rag.services;

import com.lousanter.rag.Model.Curso;
import com.lousanter.rag.Repository.CursoRepo;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CursoService {

    @Autowired private CursoRepo cursoRepo;
    @Autowired private VectorStore vectorStore;

    public Curso save(Curso curso) {
        eliminarVectorPorIdCurso(curso.getIdCurso()); // elimina si ya existe
        Curso guardado = cursoRepo.save(curso);
        guardarVector(guardado); // sincroniza con vector store
        return guardado;
    }

    public void deleteById(Long idCurso) {
        cursoRepo.deleteById(idCurso);
        eliminarVectorPorIdCurso(idCurso);
    }

    public List<Curso> findAll() {
        return cursoRepo.findAll();
    }

    public Curso findById(Long id) {
        return cursoRepo.findById(id).orElse(null);
    }

    private void guardarVector(Curso curso) {
        String contenido = String.format(
                "idCurso: %s%nnombre: %s%ncréditos: %s",
                curso.getIdCurso(), curso.getNombre(), curso.getCreditos()
        );

        Document doc = Document.builder()
                .id(UUID.randomUUID().toString())
                .text(contenido)
                .metadata(Map.of(
                        "tipo", "curso",
                        "idCurso", curso.getIdCurso()
                ))
                .build();

        vectorStore.add(List.of(doc));
    }

    private void eliminarVectorPorIdCurso(Long idCurso) {
        if (idCurso == null) return;

        Filter.Expression filter = new Filter.Expression(
                Filter.ExpressionType.AND,
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("idCurso"), new Filter.Value(idCurso)),
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("tipo"), new Filter.Value("curso"))
        );

        vectorStore.delete(filter);
    }
}
