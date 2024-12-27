package vn.test.hub.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.test.hub.common.info.ErrorInfo;
import vn.test.hub.common.response.APIResponse;
import vn.test.hub.common.utils.ResponseUtils;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResponseUtils responseUtils;

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<APIResponse<Void, ErrorInfo>> processSystemException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage());
        String message = "";

        if (e instanceof MethodArgumentNotValidException ex) {
            message = ex.getMessage();
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            message = message.substring(start + 1, end - 1);
        } else if (e instanceof MissingServletRequestParameterException ex) {
            message = "Invalid request parameter " + ex.getParameterName();
        }

        return responseUtils.generateAPIResponse(
                message,
                ErrorInfo.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .url(request.getMethod() + " " + request.getRequestURL())
                        .timestamp(Instant.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<APIResponse<Void, ErrorInfo>> processBaseException(BaseException e, HttpServletRequest request) {
        log.error(e.getMessage());
        HttpStatus httpStatus = e.getHttpStatus();
        if (e instanceof UnAuthorizedException)
            httpStatus = HttpStatus.UNAUTHORIZED;
        else if (e instanceof ForbiddenException)
            httpStatus = HttpStatus.FORBIDDEN;
        else if (e instanceof ResourceNotFoundException)
            httpStatus = HttpStatus.NOT_FOUND;

        return responseUtils.generateAPIResponse(
                e.getMessage(),
                ErrorInfo.builder()
                        .code(e.getCode())
                        .url(request.getMethod() + " " + request.getRequestURL())
                        .timestamp(Instant.now())
                        .build(),
                httpStatus
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<APIResponse<Void, ErrorInfo>> processForbiddenException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage());
        return responseUtils.generateAPIResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ErrorInfo.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .url(request.getMethod() + " " + request.getRequestURL())
                        .timestamp(Instant.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
