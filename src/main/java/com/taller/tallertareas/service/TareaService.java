package com.taller.tallertareas.service;

import com.taller.tallertareas.exception.ResourceNotFoundException;
import com.taller.tallertareas.model.Estado;
import com.taller.tallertareas.model.Tarea;
import com.taller.tallertareas.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final TareaRepository repo;

    public TareaService(TareaRepository repo) {
        this.repo = repo;
    }

    public Tarea crearTarea(Tarea tarea) {
        tarea.setId(null);
        tarea.setCreadaEn(LocalDateTime.now());
        return repo.save(tarea);
    }

    public List<Tarea> listarTareas(Optional<Estado> estado, Optional<java.time.LocalDate> antesDe) {
        if (estado.isPresent()) {
            return repo.findByEstado(estado.get());
        } else if (antesDe.isPresent()) {
            return repo.findByFechaVencimientoBefore(antesDe.get().plusDays(1)); // inclusive
        } else {
            return repo.findAll();
        }
    }

    public Tarea obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));
    }

    public Tarea actualizarTarea(Long id, Tarea datos) {
        Tarea existente = obtenerPorId(id);
        existente.setTitulo(datos.getTitulo());
        existente.setDescripcion(datos.getDescripcion());
        existente.setFechaVencimiento(datos.getFechaVencimiento());
        existente.setPrioridad(datos.getPrioridad());
        existente.setEstado(datos.getEstado());
        return repo.save(existente);
    }

    public void eliminarTarea(Long id) {
        Tarea existente = obtenerPorId(id);
        repo.delete(existente);
    }
}
