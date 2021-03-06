package perfume.webservice.perfume.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import perfume.webservice.perfume.common.domain.Perfume;

import java.util.List;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

//    @Query("select p from Perfume p   join fetch p.fragranceGroup " )
    @EntityGraph(attributePaths = {"fragranceGroup","fragranceGroup.fragrance"})
    Page<Perfume> findAll(Pageable pageable);
}
