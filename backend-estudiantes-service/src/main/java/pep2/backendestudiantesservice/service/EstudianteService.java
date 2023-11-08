package pep2.backendestudiantesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pep2.backendestudiantesservice.entity.EstudianteEntity;
import pep2.backendestudiantesservice.repository.EstudianteRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;
    public EstudianteEntity guardarEstudiante(EstudianteEntity estudiante){
        return estudianteRepository.save(estudiante);
    }
    public ArrayList<EstudianteEntity> obtenerEstudiantes(){
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }
    public EstudianteEntity obtenerEstudiantePorId(Long id){
        Optional<EstudianteEntity> optionalEstudiante = estudianteRepository.findById(id);
        // Verifica si el Optional contiene un valor y, si es así, devuélvelo, de lo contrario, devuelve null o realiza alguna otra acción apropiada.
        return optionalEstudiante.orElse(null); // Puedes cambiar "null" por lo que desees en caso de no encontrar el usuario.
    }
    public EstudianteEntity obtenerEstudiantePorRut(String rut) {
        return estudianteRepository.findByRut(rut);}
    public boolean eliminarEstudiante(Long id) {
        try{
           estudianteRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
    public int calcularPromedioPuntaje(EstudianteEntity estudiante){
        ArrayList<Integer> puntajes = estudiante.getPuntajes();
        int sumatoria=0;
        for (int i=0; i<puntajes.size();i++){
            sumatoria= sumatoria + puntajes.get(i);
        }
        estudiante.setPromedio(sumatoria/puntajes.size());
        //guardarUsuario(usuario);
        return sumatoria/puntajes.size();
    }
}
