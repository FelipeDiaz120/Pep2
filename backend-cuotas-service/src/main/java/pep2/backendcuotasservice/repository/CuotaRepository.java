package pep2.backendcuotasservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pep2.backendcuotasservice.entity.CuotaEntity;

import java.util.ArrayList;

public interface CuotaRepository extends JpaRepository<CuotaEntity, Long> {
    public CuotaEntity findByTipoPago(String tipoPago);
    public ArrayList<CuotaEntity> findAllByRutDuenoPago(String rutDuenoPago);
}

