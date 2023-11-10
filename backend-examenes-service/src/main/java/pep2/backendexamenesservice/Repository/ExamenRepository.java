package pep2.backendexamenesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pep2.backendexamenesservice.Entity.ExamenEntity;

import java.util.ArrayList;
@Repository
public interface ExamenRepository extends JpaRepository<ExamenEntity, Long> {
    public ArrayList<ExamenEntity> findAllByRutEstudiante(String rutEstudiante);
    public ExamenEntity findByRutEstudiante(String rutEstudiante);
}
