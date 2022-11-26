package ma.anas.sisba.Services.implementation;

import com.google.gson.Gson;
import ma.anas.sisba.Dto.ApiResponse;
import ma.anas.sisba.Dto.AuthDto;
import ma.anas.sisba.Entities.User;
import ma.anas.sisba.Repositories.UserRepo;
import ma.anas.sisba.Req.AuthReq;
import ma.anas.sisba.Req.SignupReq;
import ma.anas.sisba.Security.JwtTokenUtil;
import ma.anas.sisba.Services.AuthService;
import ma.anas.sisba.exception.ExceptionApi;
import ma.anas.sisba.exception.PayLoadExceptionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public AuthDto login(AuthReq req) {
        User user = userRepo.findByEmail(req.getEmail().toLowerCase()).orElseThrow(
                () ->{
                    throw new ExceptionApi(PayLoadExceptionItem.INCORRECT_CREDENTIALS);
                }
        );
        if(!user.getEnabled())
        {
            throw new ExceptionApi(PayLoadExceptionItem.DISABLED_ACCOUNT);
        }
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ExceptionApi(PayLoadExceptionItem.INCORRECT_CREDENTIALS);
        }
        else
        {
            AuthDto authDto = new AuthDto();
            authDto.setToken(jwtTokenUtil.generateLoginToken(user.getId().toString(),"User"));
            return authDto;
        }
    }

    @Override
    public ApiResponse signup(SignupReq req) {

        req.setEmail(req.getEmail().toLowerCase());
        User  userExist = userRepo.findByEmail(req.getEmail()).orElse(null);
        if (userExist != null) {
            throw new ExceptionApi(PayLoadExceptionItem.Email_Already_Used);
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(req.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepo.save(user);

        return ApiResponse.builder().id(user.getId().toString()).build();
    }
}
