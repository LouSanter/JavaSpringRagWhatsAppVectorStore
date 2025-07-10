package com.lousanter.rag.Controller;


import com.lousanter.rag.Model.Entity.HistorialAcademico;
import com.lousanter.rag.services.HistorialAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historial")
public class HistorialAcademicoController {

    @Autowired
    private HistorialAcademicoService historialService;

    @PostMapping("/crear")
    public HistorialAcademico crear(@RequestBody HistorialAcademico historial) {
        return historialService.save(historial);
    }

    @PutMapping("/actualizar")
    public HistorialAcademico actualizar(@RequestBody HistorialAcademico historial) {
        return historialService.save(historial);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        historialService.deleteById(id);
    }
}
