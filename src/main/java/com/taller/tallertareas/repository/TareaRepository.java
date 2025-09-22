package com.taller.tallertareas.repository;

import com.taller.tallertareas.model.Estado;
import com.taller.tallertareas.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByEstado(Estado estado);
    List<Tarea> findByFechaVencimientoBefore(LocalDate fecha);
}
