package pep2.backendexamenesservice.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pep2.backendexamenesservice.Entity.ExamenEntity;
import pep2.backendexamenesservice.Repository.ExamenRepository;
import pep2.backendexamenesservice.model.EstudianteEntity;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public int calcularPromedioPuntaje(EstudianteEntity estudiante){
        ArrayList<Integer> puntajes = estudiante.getPuntajes();
        int sumatoria=0;
        for (int i=0; i<puntajes.size();i++){
            sumatoria= sumatoria + puntajes.get(i);
        }
        estudiante.setPromedio(sumatoria/puntajes.size());
        //guardarEstudianteEnExamen(estudiante);
        return sumatoria/puntajes.size();
    }

    public void guardarPuntaje(){
        ArrayList<ExamenEntity> examenesEstudiante = obtenerExamenes();
        for (int i = 0; i < examenesEstudiante.size(); i++) {
            EstudianteEntity estudiante = obtenerEstudiantePorRut(examenesEstudiante.get(i).getRutEstudiante());
            if (estudiante.getPuntajes() == null) {
                ArrayList<Integer> listaPuntajes = new ArrayList<>();
                listaPuntajes.add(examenesEstudiante.get(i).getPuntaje());
                estudiante.setPuntajes(listaPuntajes);
                //guardarEstudianteEnExamen(estudiante);
            } else {
                ArrayList<Integer> listaPuntajes = estudiante.getPuntajes();
                listaPuntajes.add(examenesEstudiante.get(i).getPuntaje());
                estudiante.setPuntajes(listaPuntajes);
                //guardarEstudianteEnExamen(estudiante);
            }
            estudiante.setPromedio(calcularPromedioPuntaje(estudiante));
            guardarEstudianteEnExamen(estudiante);
            //pagoService.registrarDescuentoPromedio(usuario.getRut());

        }

    }
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;

        examenRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));

            String temp = "";
            String bfRead;

            while((bfRead = bf.readLine()) != null){
                guardarExamenDB(bfRead.split(",")[0], bfRead.split(",")[1], Integer.parseInt(bfRead.split(",")[2]));

                temp = temp + "\n" + bfRead;
            }

            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }


}
