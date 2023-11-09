package pep2.backendcuotasservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstudianteEntity {
    private String rut;
    private String nombres;
    private String apellidos;
    private String nombreColegio;
    private ArrayList<Integer> puntajes;
    private Integer promedio;
    //Municipal-Subvencionado-Privado
    private String tipoColegio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEgreso;
}
