package ma.anas.sisba.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ApiResponse {
    private String id;
    private String message;
}
