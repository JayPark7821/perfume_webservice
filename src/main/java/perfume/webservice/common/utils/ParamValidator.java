package perfume.webservice.common.utils;

import org.springframework.stereotype.Component;
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ResponseMsgType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ParamValidator {

    public void validateDupliAndExsistParams(List<Long> requestedParam, List<Long> entityIdList, String msg ) {
        // 중복값 체크
        if (requestedParam.size() != requestedParam.stream().distinct().count()) {
            Set<Long> set = new HashSet<>();
            throw new CustomIllegalArgumentException(ResponseMsgType.valueOf("DUPLI_" + msg), requestedParam.stream().filter(n -> !set.add(n))
                    .collect(Collectors.toSet()).toString());
        }
        
        // db에 없는값 체크
        if (entityIdList.size() != requestedParam.size()) {
            requestedParam.removeAll(entityIdList);
            throw new CustomIllegalArgumentException(ResponseMsgType.valueOf(msg+"_NOT_FOUND"), requestedParam.toString());
        }

    }
}
