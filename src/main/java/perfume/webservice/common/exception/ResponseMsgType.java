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
    KEYWORDGROUP_NOT_FOUND("notFound.keywordGroup"),
    CONTAIN_PERCENTAGE_NOT_FOUND("notFound.containPercentage"),
    CATEGORYGROUP_NOT_FOUND_NOT_FOUND("notFound.categoryGroup"),

    BINDING_ERROR_PERFUME("binding.error.perfume"),
    BINDING_ERROR_FRAGRANCE("binding.error.fragrance"),
    BINDING_ERROR_KEYWORD("binding.error.keyword"),
    BINDING_ERROR_KEYWORDGROUP("binding.error.keywordGroup"),
    BINDING_FAILED_SAVE("binding.fail"),
    DUPLI_KEYWORD("notAllow.dupliKeyword"),
    DUPLI_FRAGRANCE("notAllow.dupliFragrance"),

    CATEGORY_NOT_FOUND("notFound.category"),

    BINDING_ERROR_CATEGORY("binding.error.category");



    private final String msg;

}