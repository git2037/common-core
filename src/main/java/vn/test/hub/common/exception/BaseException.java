package vn.test.hub.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class BaseException extends RuntimeException {
    private int code;
    private HttpStatus httpStatus;

    protected BaseException(String message, int code) {
        super(message);
        this.code = code;
    }

    protected BaseException(String message, int code, HttpStatus status) {
        super(message);
        this.code = code;
        this.httpStatus = status;
    }
}
