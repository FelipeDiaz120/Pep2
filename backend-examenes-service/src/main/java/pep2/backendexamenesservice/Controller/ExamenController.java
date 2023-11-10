package pep2.backendexamenesservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pep2.backendexamenesservice.Entity.ExamenEntity;
import pep2.backendexamenesservice.Service.ExamenService;
import pep2.backendexamenesservice.model.EstudianteEntity;

@RestController
@RequestMapping("/examenes")
public class ExamenController {

    @Autowired
    ExamenService examenService;
    @GetMapping("/{rut}")
    public ResponseEntity<ExamenEntity> findByRut(@PathVariable("rut") String rut) {
        ExamenEntity examenEntity = examenService.obtenerPorRut(rut);
        System.out.println(examenEntity);
        return ResponseEntity.ok(examenEntity);
    }
    @PostMapping()
    public ResponseEntity<EstudianteEntity> newEstudiante(@RequestBody EstudianteEntity estudiante) {
        EstudianteEntity nuevoEstudiante = examenService.guardarEstudianteEnExamen(estudiante).getBody();
        return ResponseEntity.ok(nuevoEstudiante);
    }
}
