package pep2.backendcuotasservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep2.backendcuotasservice.entity.CuotaEntity;
import pep2.backendcuotasservice.entity.Resumen;
import pep2.backendcuotasservice.repository.ResumenRepository;

import java.util.ArrayList;

@Service
public class ResumenService {
    @Autowired
    ResumenRepository resumenRepository;
    public Resumen guardarResumen(Resumen resumen) {
        String rut = resumen.getRut();
        Resumen resumenExiste=resumenRepository.findByRut(rut);
        if(resumenExiste ==null){
            return resumenRepository.save(resumen);}
        else resumen.setId(resumenExiste.getId());
        return resumenRepository.save(resumen);

    }
    public ArrayList<Resumen> obtenerPorRut(String Rut) {
        return resumenRepository.findAllByRut(Rut);
    }
}
