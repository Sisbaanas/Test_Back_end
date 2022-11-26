package ma.anas.sisba.Req;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class TestObjectReq {

    @NotBlank
    private String name;

    @NotBlank
    private Double value;

    @NotBlank
    private String country;

    @NotBlank
    private String city;
}
