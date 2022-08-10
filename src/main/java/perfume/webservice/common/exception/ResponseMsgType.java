package perfume.webservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum ResponseMsgType {

    INVALID_TOKEN("notAllow.invalidToken"),
    NOT_EXPIRED_TOKEN_YET("notAllow.notExpiredTokenYet"),
    PERFUME_NOT_FOUND("notFound.perfume"),
    FRAGRANCE_NOT_FOUND("notFound.fragrance"),
    KEYWORD_NOT_FOUND("notFound.keyword"),
    BINDING_ERROR_PERFUME("binding.error.perfume"),
    BINDING_ERROR_FRAGRANCE("binding.error.fragrance"),
    BINDING_ERROR_KEYWORD("binding.error.keyword"),
    CONTENT_NOT_FOUND("notFound.content"),
    DUPLI_FRAGRANCE("notAllow.dupliFragrance"),
    BINDING_FAILED_SAVE("binding.fail");



    private final String msg;

}