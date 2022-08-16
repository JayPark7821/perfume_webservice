package perfume.webservice.perfume.repository;

import static org.springframework.util.StringUtils.*;
import static perfume.webservice.keyword.domain.entity.QKeywordMaster.*;
import static perfume.webservice.perfume.domain.entity.QFragrance.*;
import static perfume.webservice.perfume.domain.entity.QFragranceGroup.*;
import static perfume.webservice.perfume.domain.entity.QPerfume.*;
import static perfume.webservice.perfume.domain.entity.QPerfumeKeyword.*;

import java.util.List;
import javax.persistence.EntityManager;

import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import perfume.webservice.keyword.domain.entity.QKeywordMaster;
import perfume.webservice.perfume.domain.entity.*;
import perfume.webservice.perfume.searchcondition.FragranceSearchCondition;
import perfume.webservice.perfume.searchcondition.PerfumeSearchCondition;

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
				 ,fragranceNameContains(condition.getFragranceName())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(perfume.id.desc())
			.fetch();

		JPAQuery<Perfume> countQuery = queryFactory
			.selectFrom(perfume)
			.where(perfumeNameContains(condition.getPerfumeName())
				 ,fragranceNameContains(condition.getFragranceName())
			);

		return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
	}

	public Page<Fragrance> searchFragrancesWithCondition(FragranceSearchCondition condition, Pageable pageable) {

		List<Fragrance> results = queryFactory
				.selectFrom(fragrance)
				.where(fragranceNameContains(condition.getFragranceName()),
						fragranceDescContains(condition.getFragranceDesc())
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.orderBy(fragrance.id.desc())
				.fetch();

		JPAQuery<Fragrance> countQuery = queryFactory
				.selectFrom(fragrance)
				.where(fragranceNameContains(condition.getFragranceName()),
						fragranceDescContains(condition.getFragranceDesc())
				);

		return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
	}


	public List<Perfume> searchPerfumesWithKeywords(List<Long> keywords) {

		QPerfumeKeyword perfumeKeywordMain = new QPerfumeKeyword("perfumeKeywordMain");
		QPerfume perfumeMain = new QPerfume("perfumeMain");
		QKeywordMaster keywordMasterMain = new QKeywordMaster("keywordMasterMain");

		List<Perfume> results = queryFactory
				.selectDistinct(perfumeMain)
				.from(perfumeMain)
				.leftJoin(perfumeMain.perfumeKeyword, perfumeKeywordMain).fetchJoin()
				.innerJoin(perfumeKeywordMain.keywordMaster, keywordMasterMain).fetchJoin()
				.where(perfumeMain.id.in(
						JPAExpressions.selectDistinct(perfumeKeyword.perfume.id)
								.from(perfumeKeyword)
								.innerJoin(perfumeKeyword.keywordMaster, keywordMaster)
								.where(perfumeKeyword.keywordMaster.id.in(keywords))
								.groupBy(perfumeKeyword.perfume.id)
								.having(perfumeKeyword.count().goe(keywords.size()))))
				.orderBy(perfumeMain.id.desc())
				.fetch();

		return results;
	}

	private BooleanExpression fragranceDescContains(String desc) {
		return hasText(desc) ? fragrance.description.containsIgnoreCase(desc) : null;
	}

	private BooleanExpression fragranceNameContains(String name) {
		return hasText(name) ? fragrance.name.containsIgnoreCase(name) : null;
	}

	private BooleanExpression perfumeNameContains(String name) {
		return hasText(name) ? perfume.name.containsIgnoreCase(name) : null;
	}


}
