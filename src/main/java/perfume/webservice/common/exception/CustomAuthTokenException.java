package perfume.webservice.common.exception;

import java.util.List;

public class CustomAuthTokenException extends RuntimeException{

    private final String param;

    public CustomAuthTokenException(ResponseMsgType et, String param ) {
        super(et.getMsg());
        this.param = param;
    }

    public CustomAuthTokenException(ResponseMsgType et) {
        super(et.getMsg());
        this.param = null;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String getParam() {
        return this.param;
    }


}
