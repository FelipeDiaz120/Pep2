package pep2.backendcuotasservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pep2.backendcuotasservice.entity.CuotaEntity;
import pep2.backendcuotasservice.entity.Resumen;

import java.util.ArrayList;

@Repository
public interface ResumenRepository extends JpaRepository<Resumen, Long> {
    public ArrayList<Resumen> findAllByRut(String rut);
    public Resumen findByRut(String rut);
}
