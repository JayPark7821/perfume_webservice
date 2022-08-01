package perfume.webservice.common.exception;

public class CustomBindingException extends RuntimeException{

    private final String param;

    public CustomBindingException(ResponseMsgType et, String param ) {
        super(et.getMsg());
        this.param = param;
    }

    public CustomBindingException(ResponseMsgType et) {
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
