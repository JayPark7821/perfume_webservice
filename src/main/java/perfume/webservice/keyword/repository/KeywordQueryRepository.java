package perfume.webservice.keyword.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import perfume.webservice.category.domain.entity.QCategory;
import perfume.webservice.category.domain.entity.QCategoryGroupDetail;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.domain.entity.KeywordType;
import perfume.webservice.keyword.domain.entity.QKeywordGroup;
import perfume.webservice.keyword.domain.entity.QKeywordGroupDetail;
import perfume.webservice.keyword.searchcondition.KeywordSearchCondition;

import javax.persistence.EntityManager;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static perfume.webservice.category.domain.entity.QCategory.*;
import static perfume.webservice.category.domain.entity.QCategoryGroupDetail.*;
import static perfume.webservice.keyword.domain.entity.QKeywordGroup.*;
import static perfume.webservice.keyword.domain.entity.QKeywordGroupDetail.*;
import static perfume.webservice.keyword.domain.entity.QKeywordMaster.*;

@Repository
public class KeywordQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public KeywordQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<KeywordMaster> findKeywordsFromCategory(Long group, int level) {
        return queryFactory
                .selectFrom(keywordMaster)
                .innerJoin(keywordGroupDetail).on(keywordMaster.id.eq(keywordGroupDetail.keywordMaster.id))
                .innerJoin(keywordGroupDetail.keywordGroup, keywordGroup)
                .innerJoin(category).on(keywordGroup.id.eq(category.keywordGroup.id))
                .innerJoin(categoryGroupDetail).on(category.id.eq(categoryGroupDetail.category.id)
                        , categoryGroupDetail.level.eq(level)
                        , categoryGroupDetail.categoryGroup.id.eq(group))
                .fetch();


    }

    public Page<KeywordMaster> searchKeywordsWithCondition(KeywordSearchCondition condition, Pageable pageable) {

        List<KeywordMaster> results = queryFactory
                .selectFrom(keywordMaster)
                .where(keywordNameContains(condition.getKeywordName()),
                        keywordDescContains(condition.getKeywordDesc()),
                        keywordTypeContains(condition.getKeywordType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(keywordMaster.id.desc())
                .fetch();

        JPAQuery<KeywordMaster> countQuery = queryFactory
                .selectFrom(keywordMaster)
                .where(keywordNameContains(condition.getKeywordName()),
                        keywordDescContains(condition.getKeywordDesc()),
                        keywordTypeContains(condition.getKeywordType())
                );
        return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression keywordTypeContains(KeywordType keywordType) {
        return keywordType != null ? keywordMaster.keywordType.eq(keywordType) : null;
    }

    private BooleanExpression keywordDescContains(String keywordDesc) {
        return hasText(keywordDesc) ? keywordMaster.name.containsIgnoreCase(keywordDesc) : null;
    }

    private BooleanExpression keywordNameContains(String keywordName) {
        return hasText(keywordName) ? keywordMaster.description.containsIgnoreCase(keywordName) : null;
    }
}



