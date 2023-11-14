package pep2.backendexamenesservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pep2.backendexamenesservice.Entity.ExamenEntity;
import pep2.backendexamenesservice.Service.ExamenService;
import pep2.backendexamenesservice.model.EstudianteEntity;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping("/examenes")
public class ExamenController {

    @Autowired
    ExamenService examenService;
    @GetMapping("/estudiante/{rut}")
    public ResponseEntity<EstudianteEntity> findEstudianteByRut(@PathVariable("rut") String rut) {
        EstudianteEntity estudianteEntity = examenService.obtenerEstudiantePorRut(rut);
        System.out.println(estudianteEntity);
        return ResponseEntity.ok(estudianteEntity);
    }
    @GetMapping("/{rut}")
    public ResponseEntity<ExamenEntity> findByRut(@PathVariable("rut") String rut) {
        ExamenEntity examenEntity = examenService.obtenerPorRut(rut);
        System.out.println(examenEntity);
        return ResponseEntity.ok(examenEntity);
    }
    @GetMapping("/")
    public ResponseEntity<ArrayList<ExamenEntity>> listar() {
        ArrayList<ExamenEntity> examen=examenService.obtenerExamenes();
        return ResponseEntity.ok(examen);
    }

    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException {
        examenService.guardar(file);
       examenService.leerCsv("Examen.csv");
       examenService.guardarPuntaje();
    }
}

