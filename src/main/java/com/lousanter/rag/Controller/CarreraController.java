package com.lousanter.rag.Controller;


import com.lousanter.rag.Model.Entity.Carrera;
import com.lousanter.rag.services.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @PostMapping("/crear")
    public ResponseEntity<Carrera> crearCarrera(@RequestBody Carrera carrera) {
        Carrera nueva = carreraService.save(carrera);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Carrera>> listarCarreras() {
        return ResponseEntity.ok(carreraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> obtenerCarrera(@PathVariable Long id) {
        Carrera carrera = carreraService.findById(id);
        return carrera != null ? ResponseEntity.ok(carrera) : ResponseEntity.notFound().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Carrera> actualizarCarrera(@PathVariable Long id, @RequestBody Carrera carreraActualizada) {
        carreraActualizada.setIdCarrera(id);
        Carrera actualizada = carreraService.save(carreraActualizada);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCarrera(@PathVariable Long id) {
        carreraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
