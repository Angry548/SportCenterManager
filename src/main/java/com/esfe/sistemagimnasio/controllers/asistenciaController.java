package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Asistencia;
import com.esfe.sistemagimnasio.services.interfaces.IAsistenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class asistenciaController {

    @Autowired
    private IAsistenciaService asistenciaService;


    @GetMapping
    public ResponseEntity<List<Asistencia>> obtenerTodos() {
        List<Asistencia> asistencias = asistenciaService.obtenerTodos();
        return ResponseEntity.ok(asistencias);
    }


    @GetMapping("/paginado")
    public ResponseEntity<Page<Asistencia>> obtenerTodosPaginadas(Pageable pageable) {
        Page<Asistencia> paginas = asistenciaService.obtenerTodosPaginados(pageable);
        return ResponseEntity.ok(paginas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> obtenerPorId(@PathVariable Integer id) {
        return asistenciaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Asistencia> crear(@Valid @RequestBody Asistencia asistencia) {
        if (asistencia.getFechaHora() == null) {
            asistencia.setFechaHora(LocalDateTime.now());
        }
        Asistencia nuevaAsistencia = asistenciaService.guardar(asistencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaAsistencia);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> actualizar(@PathVariable Integer id, @Valid @RequestBody Asistencia asistencia) {
        return asistenciaService.obtenerPorId(id)
                .map(asistenciaExistente -> {
                    asistencia.setId(id);

                    if (asistencia.getFechaHora() == null) {
                        asistencia.setFechaHora(asistenciaExistente.getFechaHora());
                    }
                    Asistencia actualizada = asistenciaService.guardar(asistencia);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/validar-acceso")
    public ResponseEntity<Boolean> validarAcceso(@PathVariable Integer id) {
        try {
            boolean tieneAcceso = asistenciaService.validarAcceso(id);
            return ResponseEntity.ok(tieneAcceso);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return asistenciaService.obtenerPorId(id)
                .map(asistencia -> {
                    asistenciaService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}