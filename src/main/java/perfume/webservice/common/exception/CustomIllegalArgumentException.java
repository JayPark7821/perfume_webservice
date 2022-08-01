package perfume.webservice.common.exception;

import java.util.List;

public class CustomIllegalArgumentException extends RuntimeException{

    private final String param;

    public CustomIllegalArgumentException(ResponseMsgType et, Long id) {
        super(et.getMsg());
        this.param = Long.toString(id) ;
    }
    public CustomIllegalArgumentException(ResponseMsgType et, List<Long> ids) {
        super(et.getMsg());
        this.param = ids.toString();
    }

    public CustomIllegalArgumentException(ResponseMsgType et, String name) {
        super(et.getMsg());
        this.param = name;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String getParam() {
        return this.param;
    }
}
