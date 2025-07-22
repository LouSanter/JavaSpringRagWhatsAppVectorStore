package com.lousanter.rag.ragService;

import com.lousanter.rag.Model.*;
import com.lousanter.rag.Repository.*;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RagService {

    @Autowired
    private CarreraRepo carreraRepo;
    @Autowired
    private CicloRepo cicloRepo;
    @Autowired
    private CursoRepo cursoRepo;
    @Autowired
    private EstudianteRepo estudianteRepo;
    @Autowired
    private ProfesorRepo profesorRepo;
    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private HistorialAcademicoRepo historialRepo;

    public void indexarTodo() {
        indexarHistorialAcademicoExistente();
    }


    public void indexarHistorialAcademicoExistente() {
        List<HistorialAcademico> historiales = historialRepo.findAll();

        List<Document> documentos = historiales.stream().map(ha -> {

            Estudiante estudiante = estudianteRepo.findById(ha.getEstudiante().getIdEstudiante()).orElse(null);
            Curso curso = cursoRepo.findById(ha.getCurso().getIdCurso()).orElse(null);
            Ciclo ciclo = cicloRepo.findById(ha.getCiclo().getIdCiclo()).orElse(null);
            Profesor profesor = profesorRepo.findById(ha.getProfesor().getIdProfesor()).orElse(null);

            ha.setEstudiante(estudiante);
            ha.setCurso(curso);
            ha.setCiclo(ciclo);
            ha.setProfesor(profesor);

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
                    estudiante != null ? estudiante.getNombreCompleto() : "N/A",
                    curso != null ? curso.getNombre() : "N/A",
                    ciclo != null ? ciclo.getNombre() : "N/A",
                    profesor != null ? profesor.getNombreCompleto() : "N/A",
                    ha.getNota() != null ? ha.getNota().toString() : "Sin nota"
            );

            return Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(contenido)
                    .metadata(Map.of(
                            "tipo", "historial",
                            "idHistorialAcademico", ha.getIdHistorialAcademico()
                    ))
                    .build();
        }).toList();

        vectorStore.add(documentos);
    }




    public void indexarCarreras() {
        List<Carrera> carreras = carreraRepo.findAll();

        for (Carrera c : carreras) {
            String texto = String.format(
                    "idCarrera: %s%nNombre: %s%nDuración: %d ciclos",
                    c.getIdCarrera(), c.getNombre(), c.getDuracion()
            );


            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "carrera",
                            "idCarrera", c.getIdCarrera()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarCiclos() {
        List<Ciclo> ciclos = cicloRepo.findAll();

        for (Ciclo c : ciclos) {
            String texto = String.format(
                    "idCiclo: %s%nnombre: %s%nfechaInicio: %s%nfechaFin: %s",
                    c.getIdCiclo(), c.getNombre(), c.getFechaInicio(), c.getFechaFin()
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "ciclo",
                            "idCiclo", c.getIdCiclo()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarCursos() {
        List<Curso> cursos = cursoRepo.findAll();

        for (Curso c : cursos) {
            String texto = String.format(
                    "idCurso: %s%nnombre: %s%ncreditos: %s",
                    c.getIdCurso(), c.getNombre(), c.getCreditos()
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "curso",
                            "idCurso", c.getIdCurso()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarProfesores() {
        List<Profesor> profesores = profesorRepo.findAll();

        for (Profesor p : profesores) {
            String texto = String.format("""
                Profesor:
                ID: %s
                Nombre completo: %s
                Email: %s
                Especialidad: %s
                """,
                    p.getIdProfesor(), p.getNombreCompleto(), p.getEmail(), p.getEspecialidad()
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "profesor",
                            "idProfesor", p.getIdProfesor()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepo.findAll();

        for (Estudiante e : estudiantes) {
            Carrera carrera = e.getCarrera();

            String texto = String.format(
                    "idEstudiante: %s%nNombre: %s%nEmail: %s%nFechaIngreso: %s%nCarrera: %s",
                    e.getIdEstudiante(), e.getNombreCompleto(), e.getEmail(),
                    e.getFechaIngreso(), carrera != null ? carrera.getNombre() : "Sin carrera"
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "estudiante",
                            "idEstudiante", e.getIdEstudiante()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }
}
