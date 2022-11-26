package ma.anas.sisba.Services;

import ma.anas.sisba.Dto.ApiResponse;
import ma.anas.sisba.Dto.AuthDto;
import ma.anas.sisba.Dto.TestObjectDto;
import ma.anas.sisba.Req.AuthReq;
import ma.anas.sisba.Req.SignupReq;
import ma.anas.sisba.Req.TestObjectReq;

import java.util.List;

public interface AuthService {

    AuthDto login(AuthReq req);

    ApiResponse signup(SignupReq req);
}
