package perfume.webservice.perfume.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import perfume.webservice.perfume.domain.entity.Perfume;

import java.util.Optional;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

//    @Query("select p from Perfume p   join fetch p.fragranceGroup " )
    @EntityGraph(attributePaths = {"fragranceGroup","fragranceGroup.fragrance", "perfumeKeyword"})
    Page<Perfume> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"fragranceGroup","fragranceGroup.fragrance"})
    Optional<Perfume> findByName(String name);
}
