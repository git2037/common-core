package vn.test.hub.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.test.hub.common.info.ErrorInfo;
import vn.test.hub.common.response.APIResponse;

@Component
public class ResponseUtils {
    public <T1, T2> ResponseEntity<APIResponse<T1, T2>> generateSuccessResponse(String message, T1 data, T2 metadata) {
        return ResponseEntity.ok(
                 APIResponse.<T1, T2>builder()
                        .status("Success")
                        .message(message)
                        .data(data)
                        .metadata(metadata)
                        .build()
        );
    }

    public <T1, T2> ResponseEntity<APIResponse<T1, T2>> generateCreatedResponse(String message, T1 data, T2 metadata) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        APIResponse.<T1, T2>builder()
                                .status("Success")
                                .message(message)
                                .data(data)
                                .metadata(metadata)
                                .build()
                );
    }

    public <T>ResponseEntity<APIResponse<Void, T>> generateAPIResponse(String message, T errorInfo, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                        .body(
                                APIResponse.<Void, T>builder()
                                        .status("Error")
                                        .message(message)
                                        .metadata(errorInfo)
                                        .build()
                        );
    }

}
