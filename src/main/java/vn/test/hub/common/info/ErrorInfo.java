package vn.test.hub.common.info;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class ErrorInfo {
    private Instant timestamp;
    private String url;
    private int code;
}
