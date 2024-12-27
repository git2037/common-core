package vn.test.hub.common.exception;

public class UnAuthorizedException extends BaseException {

    public UnAuthorizedException(String message, int code) {
        super(message, code);
    }
}
