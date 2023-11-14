package pep2.backendcuotasservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resumen")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Resumen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String rut;
    private String nombres;
    private int pruebasRendidas;
    private int promedio;
    private String tipoPago;
    private int montoRestante;
    private int montoTotalPagar;
    private int montoPagado;
    private int cuotasPactadas;
    private int cuotasPagadas;
    private int cuotasAtrasadas;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaUltimoPago;}
