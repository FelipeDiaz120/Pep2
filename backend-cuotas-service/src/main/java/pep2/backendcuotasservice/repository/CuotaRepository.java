package pep2.backendcuotasservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pep2.backendcuotasservice.entity.CuotaEntity;

import java.util.ArrayList;
@Repository
public interface CuotaRepository extends JpaRepository<CuotaEntity, Long> {
    public CuotaEntity findByTipoPago(String tipoPago);
    public ArrayList<CuotaEntity> findAllByRutDuenoPago(String rutDuenoPago);
}

