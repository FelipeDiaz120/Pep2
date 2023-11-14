package pep2.backendcuotasservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pep2.backendcuotasservice.entity.CuotaEntity;
import pep2.backendcuotasservice.entity.Resumen;
import pep2.backendcuotasservice.service.CuotaService;
import pep2.backendcuotasservice.service.ResumenService;

import java.util.ArrayList;

@RestController
@RequestMapping("/cuota")
public class CuotaController {
    @Autowired
    CuotaService cuotaService;
    @Autowired
    ResumenService resumenService;

    @GetMapping("/")
    public ResponseEntity<ArrayList<CuotaEntity>> listar() {
        ArrayList<CuotaEntity> cuotaEntities = cuotaService.obtenerCuotas();
        return ResponseEntity.ok(cuotaEntities);
    }
    @GetMapping("/{rut}/{tipoPago}")
    public ResponseEntity<ArrayList<CuotaEntity>> generarPagos(@PathVariable("rut") String rut, @PathVariable("tipoPago") String tipoPago) {
        if (cuotaService.obtenerPorRut(rut).isEmpty()) {
            cuotaService.generarPagos(rut, tipoPago);
            ArrayList<CuotaEntity> cuotaEntities = cuotaService.obtenerPorRut(rut);
            return ResponseEntity.ok(cuotaEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{rut}")
    public ResponseEntity<ArrayList<CuotaEntity>> findByRut(@PathVariable("rut") String rut) {
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerPorRut(rut);
        System.out.println(cuotas);
        return ResponseEntity.ok(cuotas);
    }
    @GetMapping("/descuento/{rut}")
    public ResponseEntity<Void> descuentoPromedio(@PathVariable("rut") String rut) {
        cuotaService.registrarDescuentoPromedio(rut);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/registrar/{id}")
    public ResponseEntity<Void> registrarPago(@PathVariable("id") Long id) {
        cuotaService.registrarPago_agregarInteres(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/resumen/{rut}")
    public ResponseEntity<ArrayList<Resumen>> resumen(@PathVariable("rut") String rut) {
        Resumen resumen1 = new Resumen();
        resumen1.setFechaUltimoPago(cuotaService.ultimoPagoFecha(rut));
        resumen1.setPruebasRendidas(cuotaService.obtenerDuenoPagoPorRut(rut).getPuntajes().size());
        resumen1.setTipoPago(cuotaService.obtenerPorRut(rut).get(0).getTipoPago());
        resumen1.setCuotasAtrasadas(cuotaService.atrasos(rut));
        resumen1.setCuotasPactadas(cuotaService.obtenerPorRut(rut).get(0).getTotalCuotas());
        resumen1.setCuotasPagadas(cuotaService.calcularCuotasPagadas(rut));
        resumen1.setMontoRestante(cuotaService.montoTotal(rut)-cuotaService.calcularTotalCuotasPagadas(rut));
        resumen1.setMontoTotalPagar(cuotaService.montoTotal(rut));
        resumen1.setMontoPagado(cuotaService.calcularTotalCuotasPagadas(rut));
        resumen1.setPromedio(cuotaService.obtenerDuenoPagoPorRut(rut).getPromedio());
        resumen1.setRut(rut);
        resumen1.setNombres(cuotaService.obtenerDuenoPagoPorRut(rut).getNombres());
        resumenService.guardarResumen(resumen1);
        ArrayList<Resumen> resumenes = resumenService.obtenerPorRut(rut);
        return ResponseEntity.ok(resumenes);
    }
}
