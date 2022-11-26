package ma.anas.sisba.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload implements Serializable {
    private int responseCode;
    private String messageError;
    private String detail;
    private Integer statusCode;
    private List<java.lang.Error> errors;

    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private Date responseDate = new Date();
}
