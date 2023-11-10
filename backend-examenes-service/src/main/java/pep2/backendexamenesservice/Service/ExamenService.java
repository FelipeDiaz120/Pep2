package pep2.backendexamenesservice.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import pep2.backendexamenesservice.Entity.ExamenEntity;
import pep2.backendexamenesservice.Repository.ExamenRepository;
import pep2.backendexamenesservice.model.EstudianteEntity;


import java.util.ArrayList;

@Service
public class ExamenService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ExamenRepository examenRepository;
    private final Logger logg = LoggerFactory.getLogger(ExamenService.class);
    public ArrayList<ExamenEntity> obtenerExamenesPorRut(String Rut) {
        return examenRepository.findAllByRutEstudiante(Rut);
    }
    public ExamenEntity obtenerPorRut(String rut){
        return examenRepository.findByRutEstudiante(rut);
    }

    public ResponseEntity<EstudianteEntity> guardarEstudianteEnExamen(EstudianteEntity estudiante) {
        String url = "http://localhost:8080/estudiante";
        return restTemplate.postForEntity(url, estudiante, EstudianteEntity.class);
    }
    public EstudianteEntity obtenerEstudiantePorRut(String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                "http://localhost:8080/estudiante/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EstudianteEntity>() {}
        );
        return response.getBody();
    }

    public void guardarExamen(ExamenEntity examen){
        examenRepository.save(examen);
    }

    public void guardarExamenDB(String rut, String fechaExamen,int puntaje){
        ExamenEntity newExamen = new ExamenEntity();
        newExamen.setFechaExamen(fechaExamen);
        newExamen.setPuntaje(puntaje);
        newExamen.setRutEstudiante(rut);
        guardarExamen(newExamen);
    }
    public ArrayList<ExamenEntity> obtenerExamenes() {
        return (ArrayList<ExamenEntity>) examenRepository.findAll();
    }
}
