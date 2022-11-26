package ma.anas.sisba.Req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthReq {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
