package ma.anas.sisba.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Table {
    private Integer totalElements;
    private Integer totalPages;
    private Integer pageNumber;
    private List<?> list;
}
