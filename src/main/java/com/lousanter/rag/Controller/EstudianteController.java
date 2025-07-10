package com.lousanter.rag.Controller;


import com.lousanter.rag.Model.Entity.Estudiante;
import com.lousanter.rag.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping("/crear")
    public Estudiante crear(@RequestBody Estudiante estudiante) {
        return estudianteService.save(estudiante);
    }

    @PutMapping("/actualizar")
    public Estudiante actualizar(@RequestBody Estudiante estudiante) {
        return estudianteService.save(estudiante);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        estudianteService.deleteById(id);
    }
}
