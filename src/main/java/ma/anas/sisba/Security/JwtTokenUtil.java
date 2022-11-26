package ma.anas.sisba.Security;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateLoginToken(String autId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authId", autId);
        claims.put("Role", role);
        return doGenerateToken(autId, claims);
    }

    private String doGenerateToken(String autId, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setSubject(autId)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + SecurityConstants.EXPIRITION_TIME)
                )
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                .compact();

    }

    public String refreshToken(String token) {

        String original_token = token;

        // remove bearer
        token = token.substring(7);

        // split token into header , payload , SIGNATURE

        String[] chunks = token.split("\\.");
        String header = new String(Base64.getDecoder().decode(chunks[0]));
        String payload = new String(Base64.getDecoder().decode(chunks[1]));
        String SIGNATURE = chunks[2];

        Map<String, Object> retMap = new Gson().fromJson(
                payload, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );


        Map<String, Object> claims = new HashMap<>();
        claims.put("authId", retMap.get("authId").toString());
        claims.put("channel", retMap.get("channel").toString());



        String autId = retMap.get("authId").toString();
        return Jwts
                .builder()
                .setSubject(autId)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + SecurityConstants.EXPIRITION_TIME)
                )
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();

    }

    //validate token
    public Boolean validateToken(String token, String subject) {
        final String username = getUsernameFromToken(token);
        return (username.equals(subject) && !isTokenExpired(token));
    }


}
