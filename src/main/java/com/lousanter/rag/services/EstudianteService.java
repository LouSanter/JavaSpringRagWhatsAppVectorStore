package com.lousanter.rag.services;


import com.lousanter.rag.Model.Carrera;
import com.lousanter.rag.Model.Estudiante;
import com.lousanter.rag.Repository.CarreraRepo;
import com.lousanter.rag.Repository.EstudianteRepo;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepo estudianteRepo;

    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private CarreraRepo carreraRepo;


    public Estudiante save(Estudiante estudiante) {

        Carrera carreraCompleta = carreraRepo.findById(estudiante.getCarrera().getIdCarrera()).orElse(null);
        estudiante.setCarrera(carreraCompleta);
        eliminarVectorPorId(estudiante.getIdEstudiante());

        // Guardar en la base de datos
        Estudiante estudianteGuardado = estudianteRepo.save(estudiante);

        // Guardar en el vector store
        guardarVector(estudianteGuardado);

        return estudianteGuardado;
    }

    public void deleteById(Long idEstudiante) {
        eliminarVectorPorId(idEstudiante);
        estudianteRepo.deleteById(idEstudiante);
    }

    private void guardarVector(Estudiante estudiante) {
        String contenido = String.format(
                "idEstudiante: %s%nNombre: %s%nEmail: %s%nFechaIngreso: %s%nCarrera: %s",
                estudiante.getIdEstudiante(),
                estudiante.getNombreCompleto(),
                estudiante.getEmail(),
                estudiante.getFechaIngreso(),
                estudiante.getCarrera() != null ? estudiante.getCarrera().getNombre() : "Sin carrera"
        );

        Document doc = Document.builder()
                .id(UUID.randomUUID().toString())
                .text(contenido)
                .metadata(Map.of(
                        "tipo", "estudiante",
                        "idEstudiante", estudiante.getIdEstudiante()
                ))
                .build();

        vectorStore.add(List.of(doc));
    }

    private void eliminarVectorPorId(Long idEstudiante) {
        if (idEstudiante == null) return;

        Filter.Expression expr = new Filter.Expression(
                Filter.ExpressionType.AND,
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("idEstudiante"), new Filter.Value(idEstudiante)),
                new Filter.Expression(Filter.ExpressionType.EQ, new Filter.Key("tipo"), new Filter.Value("estudiante"))
        );

        vectorStore.delete(expr);
    }
}
