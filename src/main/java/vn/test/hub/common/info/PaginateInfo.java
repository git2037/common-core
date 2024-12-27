package vn.test.hub.common.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginateInfo {
    private int limit;
    @JsonProperty("current_page")
    private int currentPage;
    @JsonProperty("total_page")
    private int totalPages;
}
