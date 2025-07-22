package com.lousanter.rag.AI.tools;

import com.lousanter.rag.Model.Ciclo;
import com.lousanter.rag.Model.Curso;
import com.lousanter.rag.Model.Estudiante;
import com.lousanter.rag.Model.HistorialAcademico;
import com.lousanter.rag.Repository.*;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AiTools {

    @Autowired
    EstudianteRepo estudianteRepo;
    @Autowired
    CicloRepo cicloRepo;
    @Autowired
    CursoRepo cursoRepo;
    @Autowired
    ProfesorRepo profesorRepo;
    @Autowired
    HistorialAcademicoRepo historialAcademicoRepo;

    @Tool(description = "Contar todos los estudiantes disponibles")
    long contarEstudiantes() {
        return estudianteRepo.count();
    }

    @Tool(description = "Contar todos los cursos registrados")
    long contarCursos() {
        return cursoRepo.count();
    }

    @Tool(description = "Contar todos los profesores registrados")
    long contarProfesores() {
        return profesorRepo.count();
    }

    @Tool(description = "Listar todos los ciclos académicos disponibles")
    List<Ciclo> listarCiclos() {
        return cicloRepo.findAll();
    }

    @Tool(description = "Listar estudiantes de un ciclo específico. Requiere el nombre del ciclo como parámetro.")
    List<Estudiante> estudiantesPorCiclo(String nombreCiclo) {
        return estudianteRepo.findEstudiantesPorCiclo(nombreCiclo);
    }

    @Tool(description = "Buscar cursos que coincidan con un nombre parcial. Requiere parte del nombre como parámetro.")
    List<Curso> buscarCursosPorNombre(String nombreParcial) {
        return cursoRepo.buscarPorNombre(nombreParcial);
    }

    @Tool(description = "Listar el historial académico de un estudiante. Requiere el ID del estudiante.")
    List<HistorialAcademico> historialDeEstudiante(Long estudianteId) {
        return historialAcademicoRepo.findByEstudianteIdEstudiante(estudianteId);
    }

    @Tool(description = "Calcular el promedio de notas de un estudiante. Requiere el ID del estudiante.")
    double promedioNotas(Long estudianteId) {
        List<HistorialAcademico> historial = historialAcademicoRepo.findByEstudianteIdEstudiante(estudianteId);
        if (historial.isEmpty()) return 0.0;
        double suma = historial.stream()
                .mapToDouble(h -> h.getNota().doubleValue())
                .sum();
        return Math.round((suma / historial.size()) * 100.0) / 100.0;
    }

}
