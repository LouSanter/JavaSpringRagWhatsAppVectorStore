package com.lousanter.rag.Controller;

import com.lousanter.rag.Model.Entity.Profesor;
import com.lousanter.rag.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profesor")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping("/crear")
    public Profesor crear(@RequestBody Profesor profesor) {
        return profesorService.save(profesor);
    }

    @PutMapping("/actualizar")
    public Profesor actualizar(@RequestBody Profesor profesor) {
        return profesorService.save(profesor);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        profesorService.deleteById(id);
    }
}
