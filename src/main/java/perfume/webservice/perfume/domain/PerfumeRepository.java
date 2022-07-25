package perfume.webservice.perfume.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    @Query("select p from Perfume p order by p.id desc")
    List<Perfume> findAllDesc();
}
