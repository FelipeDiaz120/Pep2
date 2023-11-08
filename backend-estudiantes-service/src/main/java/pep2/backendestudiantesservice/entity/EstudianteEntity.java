package pep2.backendestudiantesservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "estudiantes")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

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
