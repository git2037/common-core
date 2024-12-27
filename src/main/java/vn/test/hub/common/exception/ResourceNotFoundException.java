package vn.test.hub.common.exception;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message, int code) {
        super(message, code);
    }
}
