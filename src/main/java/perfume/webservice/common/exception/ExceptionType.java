package perfume.webservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionType {

    PERFUME_NOT_FOUND("notFound.perfume"),
    FRAGRANCE_NOT_FOUND("notFound.fragrance"),
    CONTENT_NOT_FOUND("notFound.content"),
    DUPLI_FRAGRANCE("notAllow.dupliFragrance");

    private final String msg;


}