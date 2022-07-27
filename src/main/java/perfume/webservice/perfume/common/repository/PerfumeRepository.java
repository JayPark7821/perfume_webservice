package perfume.webservice.perfume.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import perfume.webservice.perfume.common.domain.Perfume;

import java.util.List;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    @Query("select p from Perfume p order by p.id desc")
    List<Perfume> findAllDesc();
}
