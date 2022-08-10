package perfume.webservice.keyword.domain.dto.save;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class KeywordSaveDtoTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void 저장_테스트 () throws Exception {
        //given


        //when

        //then
    }

}