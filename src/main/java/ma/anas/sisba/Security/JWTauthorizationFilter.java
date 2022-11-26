package ma.anas.sisba.Security;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class JWTauthorizationFilter extends OncePerRequestFilter {

    private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
        //pr autoriser les entetes   + "Access-Control-Allow-Methods: GET, POST, PATCH,DELETE ..."
        response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PATCH,DELETE");
        response.addHeader("Access-Control-Allow-Headers",
                "Origin,Accept,X-Requested-With,Content-Type,"
                        + "Acess-Control-Request-Method,"
                        + "Acces-Control-Request-Headers,"
                        + "Authorization");


        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
        {
            if(request.getRequestURI().startsWith("/auth") || request.getRequestURI().startsWith("/swagger-ui")
                || request.getRequestURI().startsWith("/docs")  || request.getRequestURI().startsWith("/public"))
            {
                filterChain.doFilter(request, response);
            }
            else
            {
                //remove bearer if exist
                String JWtToken = request.getHeader(SecurityConstants.HEADER_STRING);
                Jws<Claims> jws;
                if(JWtToken != null)
                {
                    JWtToken = JWtToken.replace(SecurityConstants.TOKEN_PREFIX,"");
                    try{
                        jws = Jwts.parserBuilder()
                                .setSigningKey(SecurityConstants.SECRET)
                                .build()
                                .parseClaimsJws(JWtToken);

                        //List<String> list = new ArrayList<String>(Arrays.asList(request.getRequestURI().split("/")));
                        //request.setAttribute("id",jws.getBody().get("id"));
                        filterChain.doFilter(request, response);
                    }catch (JwtException ex) {
                        System.out.println("JwtException");
                        response.setStatus(403);
                    }
                }
                else
                {
                    response.setStatus(403);
                }
            }
        }
    }
}
