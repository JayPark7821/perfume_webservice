package perfume.webservice.perfume.common.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import perfume.webservice.perfume.admin.dto.PerfumeResponseDto;
import perfume.webservice.perfume.common.domain.*;
import perfume.webservice.perfume.common.searchcondition.PerfumeSearchCondition;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;
import static perfume.webservice.perfume.common.domain.QFragrance.*;
import static perfume.webservice.perfume.common.domain.QFragranceGroup.*;
import static perfume.webservice.perfume.common.domain.QPerfume.*;

@Repository
public class PerfumeQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PerfumeQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<Perfume> searchPerfumesWithCondition(PerfumeSearchCondition condition, Pageable pageable) {

            List<Perfume> results = queryFactory
                    .selectFrom(perfume)
                    .leftJoin(perfume.fragranceGroup, fragranceGroup).fetchJoin()
                    .leftJoin(fragranceGroup.fragrance, fragrance).fetchJoin()
                    .where(perfumeNameEq(condition.getName()),
                            fragranceNameEq(condition.getFragranceName()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(perfume.id.asc())
                    .fetch();

        JPAQuery<Perfume> countQuery = queryFactory
                .selectFrom(perfume)
                .where(perfumeNameEq(condition.getName()),
                        fragranceNameEq(condition.getFragranceName()));

        return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression fragranceNameEq(String fragranceName) {
        return hasText(fragranceName) ? fragrance.fragranceName.contains(fragranceName) : null;
    }

    private BooleanExpression perfumeNameEq(String name) {
        return hasText(name) ? perfume.name.contains(name) : null;
    }
}
