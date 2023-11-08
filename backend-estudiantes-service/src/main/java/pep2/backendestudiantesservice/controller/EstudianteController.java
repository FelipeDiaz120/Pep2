package pep2.backendestudiantesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pep2.backendestudiantesservice.entity.EstudianteEntity;
import pep2.backendestudiantesservice.service.EstudianteService;

import java.util.ArrayList;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;
    @PostMapping()
    public ResponseEntity<EstudianteEntity> newEstudiante(@RequestBody EstudianteEntity estudiante) {
        EstudianteEntity nuevoEstudiante = estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.ok(nuevoEstudiante);
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<EstudianteEntity>> listar() {
        ArrayList<EstudianteEntity> estudianteEntities = estudianteService.obtenerEstudiantes();
        return ResponseEntity.ok(estudianteEntities);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<EstudianteEntity> findByRut(@PathVariable("rut") String rut) {
        EstudianteEntity estudianteEntity = estudianteService.obtenerEstudiantePorRut(rut);
        System.out.println(estudianteEntity);
        return ResponseEntity.ok(estudianteEntity);
    }
}
