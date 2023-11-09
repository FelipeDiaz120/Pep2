package pep2.backendcuotasservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pep2.backendcuotasservice.entity.CuotaEntity;
import pep2.backendcuotasservice.service.CuotaService;

import java.util.ArrayList;

@RestController
@RequestMapping("/cuota")
public class CuotaController {
    @Autowired
    CuotaService cuotaService;
    @GetMapping("/")
    public ResponseEntity<ArrayList<CuotaEntity>> listar() {
        ArrayList<CuotaEntity> cuotaEntities = cuotaService.obtenerCuotas();
        return ResponseEntity.ok(cuotaEntities);
    }
}
