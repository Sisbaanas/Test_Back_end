package ma.anas.sisba.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String field;
    private String message;
}
