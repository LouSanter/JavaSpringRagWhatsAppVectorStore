package com.lousanter.rag.Controller;

import com.lousanter.rag.services.CicloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lousanter.rag.Model.Ciclo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ciclos")
public class CicloController {

    @Autowired
    private CicloService cicloService;

    // Crear un nuevo ciclo
    @PostMapping("/crear")
    public ResponseEntity<Ciclo> crearCiclo(@RequestBody Ciclo ciclo) {
        Ciclo nuevo = cicloService.save(ciclo);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener todos los ciclos
    @GetMapping("/listar")
    public ResponseEntity<List<Ciclo>> listarCiclos() {
        return ResponseEntity.ok(cicloService.findAll());
    }

    // Obtener un ciclo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Ciclo>> obtenerCiclo(@PathVariable Long id) {
        Optional<Ciclo> ciclo = cicloService.findById(id);
        if (ciclo.isPresent()) {
            return ResponseEntity.ok(ciclo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar un ciclo
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Ciclo> actualizarCiclo(@PathVariable Long id, @RequestBody Ciclo cicloActualizado) {
        cicloActualizado.setIdCiclo(id); // asegurar que el ID sea el correcto
        Ciclo actualizado = cicloService.save(cicloActualizado); // también sincroniza vectorStore
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un ciclo
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCiclo(@PathVariable Long id) {
        cicloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
