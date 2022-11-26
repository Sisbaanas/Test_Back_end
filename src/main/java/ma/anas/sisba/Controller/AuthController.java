package ma.anas.sisba.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.anas.sisba.Dto.ApiResponse;
import ma.anas.sisba.Dto.AuthDto;
import ma.anas.sisba.Dto.TestObjectDto;
import ma.anas.sisba.Req.AuthReq;
import ma.anas.sisba.Req.SignupReq;
import ma.anas.sisba.Req.TestObjectReq;
import ma.anas.sisba.Services.AuthService;
import ma.anas.sisba.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class    AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    @Operation(summary = "Sign in the app")
    public ResponseEntity<AuthDto> auth(@Valid @RequestBody AuthReq req) {
        return new ResponseEntity<>(authService.login(req), HttpStatus.OK);
    }

    @PostMapping("/signup")
    @Operation(summary = "create a new account ")
    public ResponseEntity<ApiResponse> sellerRegistration(@Valid @RequestBody SignupReq req) {
        return new ResponseEntity<>(authService.signup(req), HttpStatus.OK);
    }

}
