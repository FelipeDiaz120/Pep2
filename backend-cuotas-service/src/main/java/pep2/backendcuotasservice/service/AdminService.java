package pep2.backendcuotasservice.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pep2.backendcuotasservice.entity.CuotaEntity;
import pep2.backendcuotasservice.model.EstudianteEntity;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Data
@Service
public class AdminService {
    @Autowired
    CuotaService cuotaService;
    public int descuentoPromedio(CuotaEntity cuota) {
        EstudianteEntity estudiante = cuotaService.obtenerDuenoPagoPorRut(cuota.getRutDuenoPago());
        int promedio = estudiante.getPromedio();
        int descuento = 0;
        if (950 <= promedio && promedio <= 1000) {
            descuento = (int) (cuota.getMonto() - cuota.getMonto() * 0.10);
            return descuento;
        }
        if (900 <= promedio && promedio <= 949) {
            descuento = (int) (cuota.getMonto() - cuota.getMonto() * 0.05);
            return descuento;
        }
        if (850 <= promedio && promedio <= 899) {
            descuento = (int) (cuota.getMonto() - cuota.getMonto() * 0.02);
            return descuento;
        }
        if (promedio < 850){
            descuento= cuota.getMonto();
            return  descuento;
        }
        return descuento;
    }
    public int mesesDiferencia(CuotaEntity cuota){
        LocalDate fechaEmision = cuota.getFechaEmision();
        LocalDate fechaPago = cuota.getFechaPago();
        int diferenciaEnMeses = (int) ChronoUnit.MONTHS.between(fechaEmision, fechaPago);
        return diferenciaEnMeses;
    }
    public int interesAtraso(CuotaEntity cuota){
        int mesesAtraso= mesesDiferencia(cuota);
        int interes= 0;

        if (mesesAtraso == 0){
            interes= (cuota.getMonto());
            return interes;
        }
        if (mesesAtraso == 1){
            interes= (int) (cuota.getMonto() + cuota.getMonto() * 0.03);
            return interes;
        }
        if (mesesAtraso == 2){
            interes= (int) (cuota.getMonto() + cuota.getMonto() * 0.06);
            return interes;
        }
        if (mesesAtraso == 3){
            interes= (int) (cuota.getMonto() +cuota.getMonto() * 0.09);
            return interes;
        }
        if (mesesAtraso > 3){
            interes= (int) (cuota.getMonto() +cuota.getMonto() * 0.15);
            return interes;
        }
        if (mesesAtraso < 0){
            interes= (int) (cuota.getMonto() +cuota.getMonto() * 0.15);
            return 1;
        }
        return interes;
    }
    public int calcularAnosEgreso(EstudianteEntity estudiante){

        LocalDate ahora = LocalDate.now();
        LocalDate fechaEgreso = estudiante.getFechaEgreso();
        Period periodo = Period.between(fechaEgreso, ahora);
        int diferenciaEnAños = periodo.getYears();
        return diferenciaEnAños;
    }
    public int descuentoAñosEgreso(EstudianteEntity estudiante){
        int añosDeEgreso= calcularAnosEgreso(estudiante);
        int dcto= 0;
        if (añosDeEgreso < 1){
            dcto= (int) (1500000 * 0.15);
            return dcto;
        }
        if (añosDeEgreso == 1  || añosDeEgreso==2 ){
            dcto= (int) (1500000 * 0.08);
            return dcto;
        }
        if (añosDeEgreso == 3  || añosDeEgreso==4 ){
            dcto= (int) (1500000 * 0.04);
            return dcto;
        }
        if (añosDeEgreso >= 5 ){
            dcto= 0;
            return dcto;
        }
        return dcto;
    }
    public int descuentoColegio(EstudianteEntity estudiante){

        int dcto = 0;
        String tipoColegio = estudiante.getTipoColegio();
        if (tipoColegio.equals("Municipal")){
            dcto = (int) (1500000 * 0.20);
            return dcto;
        }
        if (tipoColegio.equals("Subvencionado")){
            dcto = (int) (1500000 * 0.10);
            return dcto;
        }
        if (tipoColegio=="Privado"){
            dcto =  0;
            return dcto;
        }
        return dcto;
    }

}
