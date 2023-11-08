package pep2.backendestudiantesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pep2.backendestudiantesservice.entity.EstudianteEntity;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    public EstudianteEntity findByRut(String rut);
}
