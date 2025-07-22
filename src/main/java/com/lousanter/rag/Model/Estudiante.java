package com.lousanter.rag.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idEstudiante;

    private String nombreCompleto;
    private String email;
    private String fechaIngreso;

    @ManyToOne
    @JoinColumn(name = "idCarrera", referencedColumnName = "id")
    private Carrera carrera;





}
