package perfume.webservice.category.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.category.domain.entity.QCategory;
import perfume.webservice.category.searchcondition.CategorySearchCondition;
import perfume.webservice.keyword.domain.entity.KeywordMaster;

import javax.persistence.EntityManager;

import static perfume.webservice.category.domain.entity.QCategory.*;

@Repository
public class CategoryQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CategoryQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    public Page<Category> findCategoriesByCondition(CategorySearchCondition condition, Pageable pageable) {

        queryFactory
                .selectFrom(category)
                .where(categoryNameCotains(condition.getCategoryName()),
                        categoryDescContains(condition.getCategoryDesc()),
                        childCategoryIdEq(condition.getChildCategoryId()),
                        keywordGroupIdEq(condition.getKeywordGroupId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return null;
    }

    private BooleanExpression keywordGroupIdEq(Long keywordGroupId) {
        return null;
    }

    private BooleanExpression childCategoryIdEq(Long childCategoryId) {
        return null;
    }

    private BooleanExpression categoryDescContains(String categoryDesc) {
        return null;
    }

    private BooleanExpression categoryNameCotains(String categoryName) {
        return null;
    }
}
