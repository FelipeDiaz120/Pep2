package pep2.backendcuotasservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pep2.backendcuotasservice.entity.CuotaEntity;
import pep2.backendcuotasservice.model.EstudianteEntity;
import pep2.backendcuotasservice.repository.CuotaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CyclicBarrier;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotasRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    RestTemplate restTemplate;
    public CuotaEntity guardarCuota(CuotaEntity cuota) {
        return cuotasRepository.save(cuota);
    }
    public EstudianteEntity obtenerDuenoPagoPorRut(String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                "http://localhost:8080/estudiante/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EstudianteEntity>() {}
        );
        return response.getBody();
    }
    public ArrayList<CuotaEntity> obtenerCuotas() {
        return (ArrayList<CuotaEntity>) cuotasRepository.findAll();
    }

    public ArrayList<CuotaEntity> obtenerPorRut(String Rut) {
        return cuotasRepository.findAllByRutDuenoPago(Rut);
    }
    public CuotaEntity obtenerCuotaPorId(Long id){
        Optional<CuotaEntity> optionalPago = cuotasRepository.findById(id);

        // Verifica si el Optional contiene un valor y, si es así, devuélvelo, de lo contrario, devuelve null o realiza alguna otra acción apropiada.
        return optionalPago.orElse(null); // Puedes cambiar "null" por lo que desees en caso de no encontrar el usuario.
    }

    public void generarPagos(String rut, String tipoPago) {
        EstudianteEntity estudiante = obtenerDuenoPagoPorRut(rut);
        LocalDate fechaEmisionCuotas = LocalDate.of(2023,3,10);
        int cuotas = 0;
        if (tipoPago.equals("Cuotas")) {
            String tipoColegio = estudiante.getTipoColegio();
            if (tipoColegio.equals("Municipal")) {
                for (int i = 10; i > 0; i = i - 1) {
                    CuotaEntity cuota = new CuotaEntity();
                    cuotas = 10;
                    cuota.setTotalCuotas(cuotas);
                    cuota.setRutDuenoPago(estudiante.getRut());
                    cuota.setTipoPago(tipoPago);
                    cuota.setMonto((1500000 - (adminService.descuentoAñosEgreso(estudiante) + adminService.descuentoColegio(estudiante))) / cuotas);
                    cuota.setFechaPago(null);
                    cuota.setFechaEmision(fechaEmisionCuotas);
                    cuota.setEstado("Pendiente");
                    guardarCuota(cuota);
                    fechaEmisionCuotas= fechaEmisionCuotas.plusMonths(1);
                }return ;
            }
            if (tipoColegio.equals("Subvencionado")) {
                for (int i = 7; i > 0; i = i - 1) {
                    CuotaEntity cuota = new CuotaEntity();
                    cuotas = 7;
                    cuota.setTotalCuotas(cuotas);
                    cuota.setRutDuenoPago(estudiante.getRut());
                    cuota.setTipoPago(tipoPago);
                    cuota.setMonto((1500000 - (adminService.descuentoAñosEgreso(estudiante) + adminService.descuentoColegio(estudiante))) / cuotas);
                    cuota.setFechaPago(null);
                    cuota.setFechaEmision(fechaEmisionCuotas);
                    cuota.setEstado("Pendiente");
                    guardarCuota(cuota);
                    fechaEmisionCuotas= fechaEmisionCuotas.plusMonths(1);

                }return ;
            }
            if (tipoColegio.equals("Privado")) {
                for (int i = 4; i > 0; i = i - 1) {
                    CuotaEntity cuota = new CuotaEntity();
                    cuotas = 4;
                    cuota.setTotalCuotas(cuotas);
                    cuota.setRutDuenoPago(estudiante.getRut());
                    cuota.setTipoPago(tipoPago);
                    cuota.setMonto((1500000 - (adminService.descuentoAñosEgreso(estudiante) + adminService.descuentoColegio(estudiante))) / cuotas);
                    cuota.setFechaPago(null);
                    cuota.setFechaEmision(fechaEmisionCuotas);
                    cuota.setEstado("Pendiente");
                    guardarCuota(cuota);
                    fechaEmisionCuotas= fechaEmisionCuotas.plusMonths(1);

                }
                return ;
            }
        }
        if (tipoPago.equals("Contado")) {
            cuotas = 0;
            CuotaEntity cuota = new CuotaEntity();
            cuota.setTotalCuotas(cuotas);
            cuota.setRutDuenoPago(estudiante.getRut());
            cuota.setTipoPago(tipoPago);
            cuota.setMonto((820000));
            cuota.setFechaPago(fechaEmisionCuotas);
            cuota.setFechaEmision(fechaEmisionCuotas);
            cuota.setEstado("Pagado");
            guardarCuota(cuota);
        }
    }
    public void registrarPago_agregarInteres(Long id){
        CuotaEntity cuota = obtenerCuotaPorId(id);
        ArrayList<CuotaEntity> cuotas = obtenerPorRut(cuota.getRutDuenoPago());
        cuota.setFechaPago(LocalDate.now());
        int montoConInteres = adminService.interesAtraso(cuota);
        for (int i = 0; i < cuotas.size(); i++) {
            CuotaEntity pagoPendiente = cuotas.get(i);
            if (pagoPendiente.getEstado().equals("Pendiente")){
                pagoPendiente.setMonto(montoConInteres);
                guardarCuota(pagoPendiente);
            }
        }
        cuota.setEstado("Pagado");
        guardarCuota(cuota);
    }
    public void registrarDescuentoPromedio(String rut){
        ArrayList<CuotaEntity> cuotas = obtenerPorRut(rut);

        for (int i = 0; i < cuotas.size(); i++) {
            int montoConDescuento = adminService.descuentoPromedio(cuotas.get(i));
            CuotaEntity cuotaPendiente = cuotas.get(i);
            if (cuotaPendiente.getEstado().equals("Pendiente")){
                cuotaPendiente.setMonto(montoConDescuento);
                guardarCuota(cuotaPendiente);
            }
        }
    }
    public int atrasos(String rut){
        ArrayList<CuotaEntity> cuotas = obtenerPorRut(rut);
        LocalDate fechaActual = LocalDate.now();
        int atrasos = 0;
        for (CuotaEntity cuota : cuotas) {
            if (cuota.getEstado().equals("Pagado") && cuota.getFechaEmision().isBefore(cuota.getFechaPago())) {
                atrasos++;
            }
        }
        return atrasos;
    }
    public LocalDate ultimoPagoFecha(String rut){
        ArrayList<CuotaEntity> cuotas = obtenerPorRut(rut);
        LocalDate fecha = null;
        int mes = 0;
        for (CuotaEntity cuota : cuotas) {
            if (cuota.getEstado().equals("Pagado") && cuota.getFechaPago().getMonthValue() > mes) {
                mes = cuota.getFechaPago().getMonthValue();
                fecha=cuota.getFechaPago();
            }
        }
        return fecha;
    }
    public int calcularTotalCuotasPagadas(String rut){
        ArrayList<CuotaEntity> cuotas = obtenerPorRut(rut);
        int total=0;
        for (int i = 0; i < cuotas.size(); i++) {
            if (cuotas.get(i).getEstado().equals("Pagado")){
                total= total + cuotas.get(i).getMonto();
            }
        }
        return total;
    }
    public int calcularCuotasPagadas(String rut){
        ArrayList<CuotaEntity> cuotas = obtenerPorRut(rut);
        int cuotasPagadas=0;
        for (int i = 0; i < cuotas.size(); i++) {
            if (cuotas.get(i).getEstado().equals("Pagado")){
                cuotasPagadas++;
            }
        }
        return cuotasPagadas;
    }

    public int montoTotal(String rut){
        int total = 0;
        ArrayList<CuotaEntity> cuotas = obtenerPorRut(rut);
        for (int i = 0; i < cuotas.size(); i++) {
            total = total + cuotas.get(i).getMonto();
        }
        return total;
    }

}
