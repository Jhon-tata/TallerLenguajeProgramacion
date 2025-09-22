package com.taller.tallertareas.controller;

import com.taller.tallertareas.model.Estado;
import com.taller.tallertareas.model.Tarea;
import com.taller.tallertareas.service.TareaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService service;

    public TareaController(TareaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@Valid @RequestBody Tarea tarea) {
        Tarea creado = service.crearTarea(tarea);
        return ResponseEntity.created(URI.create("/tareas/" + creado.getId())).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> listar(
            @RequestParam Optional<Estado> estado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> antesDe) {
        return ResponseEntity.ok(service.listarTareas(estado, antesDe));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @Valid @RequestBody Tarea tarea) {
        return ResponseEntity.ok(service.actualizarTarea(id, tarea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }
}
