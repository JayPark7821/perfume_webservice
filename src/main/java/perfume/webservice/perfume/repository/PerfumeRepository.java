package perfume.webservice.perfume.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfume.webservice.perfume.domain.entity.Perfume;

import java.util.Optional;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

}
