package perfume.webservice.perfume.common.repository;

import static org.springframework.util.StringUtils.*;
import static perfume.webservice.perfume.common.domain.QFragrance.*;
import static perfume.webservice.perfume.common.domain.QFragranceGroup.*;
import static perfume.webservice.perfume.common.domain.QPerfume.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import perfume.webservice.perfume.common.domain.Perfume;
import perfume.webservice.perfume.common.searchcondition.PerfumeSearchCondition;

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
			.select(perfume)
			.from(perfume)
			.leftJoin(perfume.fragranceGroup, fragranceGroup).fetchJoin()
			.leftJoin(fragranceGroup.fragrance, fragrance).fetchJoin()
			.where(perfumeNameContains(condition.getPerfumeName())
				// ,fragranceNameContains(condition.getFragranceName())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(perfume.id.asc())
			.fetch();

		JPAQuery<Perfume> countQuery = queryFactory
			.selectFrom(perfume)
			.where(perfumeNameContains(condition.getPerfumeName())
				// ,fragranceNameContains(condition.getFragranceName())
			);

		return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
	}

	private BooleanExpression fragranceNameContains(String name) {
		return hasText(name) ? fragrance.name.contains(name) : null;

	}

	private BooleanExpression perfumeNameContains(String name) {
		return hasText(name) ? perfume.name.contains(name) : null;
	}
}
