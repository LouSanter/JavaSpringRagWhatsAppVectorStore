package com.lousanter.rag.services;


import com.lousanter.rag.Model.*;
import com.lousanter.rag.Repository.*;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HistorialAcademicoService {

    @Autowired
    private HistorialAcademicoRepo historialRepo;

    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private EstudianteRepo estudianteRepo;
    @Autowired
    private CursoRepo cursoRepo;
    @Autowired
    private CicloRepo cicloRepo;
    @Autowired
    private ProfesorRepo profesorRepo;

    public HistorialAcademico save(HistorialAcademico historial) {


        Estudiante estudianteCompleto = estudianteRepo.findById(historial.getEstudiante().getIdEstudiante()).orElse(null);
        historial.setEstudiante(estudianteCompleto);
        Curso cursoCompleto = cursoRepo.findById(historial.getCurso().getIdCurso()).orElse(null);
        historial.setCurso(cursoCompleto);
        Ciclo cicloCompleto = cicloRepo.findById(historial.getCiclo().getIdCiclo()).orElse(null);
        historial.setCiclo(cicloCompleto);
        Profesor profesorCompleto = profesorRepo.findById(historial.getProfesor().getIdProfesor()).orElse(null);
        historial.setProfesor(profesorCompleto);


        // Eliminar documento anterior si existe
        eliminarVectorPorId(historial.getIdHistorialAcademico());

        // Guardar en BD
        HistorialAcademico guardado = historialRepo.save(historial);

        // Guardar en VectorStore
        guardarVector(guardado);

        return guardado;
    }

    public void deleteById(Long idHistorial) {
        eliminarVectorPorId(idHistorial);
        historialRepo.deleteById(idHistorial);
    }

    private void guardarVector(HistorialAcademico ha) {
        String contenido = String.format("""
                Historial Académico:
                ID: %s
                Estudiante: %s
                Curso: %s
                Ciclo: %s
                Profesor: %s
                Nota: %s
                """,
                ha.getIdHistorialAcademico(),
                ha.getEstudiante() != null ? ha.getEstudiante().getNombreCompleto() : "N/A",
                ha.getCurso() != null ? ha.getCurso().getNombre() : "N/A",
                ha.getCiclo() != null ? ha.getCiclo().getNombre() : "N/A",
                ha.getProfesor() != null ? ha.getProfesor().getNombreCompleto() : "N/A",
                ha.getNota() != null ? ha.getNota().toString() : "Sin nota"
        );

        Document doc = Document.builder()
                .id(UUID.randomUUID().toString())
                .text(contenido)
                .metadata(Map.of(
                        "tipo", "historial",
                        "idHistorialAcademico", ha.getIdHistorialAcademico()
                ))
                .build();

        vectorStore.add(List.of(doc));
    }

    private void eliminarVectorPorId(Long idHistorial) {
        if (idHistorial == null) return;

        Filter.Expression expr = new Filter.Expression(
                Filter.ExpressionType.AND,
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("idHistorialAcademico"), new Filter.Value(idHistorial)),
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("tipo"), new Filter.Value("historial"))
        );

        vectorStore.delete(expr);
    }
}
