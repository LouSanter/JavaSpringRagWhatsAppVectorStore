package com.lousanter.rag.Model.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HistorialAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idHistorialAcademico;

    @ManyToOne
    @JoinColumn(name = "idEstudiante")
    private Estudiante estudiante;
    @ManyToOne
    @JoinColumn(name = "idCurso")
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "idCiclo")
    private Ciclo ciclo;
    @ManyToOne
    @JoinColumn(name = "idProfesor")
    private Profesor profesor;

    private BigDecimal nota;
}
