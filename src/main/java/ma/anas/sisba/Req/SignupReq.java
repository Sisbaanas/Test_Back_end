package ma.anas.sisba.Req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignupReq {

    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
