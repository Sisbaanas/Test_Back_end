package ma.anas.sisba.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestObjectDto {
    private String id;
    private String name;
    private String value;
    private String country;
    private String city;
}
