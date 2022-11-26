package ma.anas.sisba.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonInclude(value = Include.NON_NULL)
public class ChartDto {
    private List<String> labels;
    private List<Double> data;
}
