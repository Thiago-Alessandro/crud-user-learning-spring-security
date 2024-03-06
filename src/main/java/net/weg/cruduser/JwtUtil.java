package net.weg.cruduser;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Date;

public class JwtUtil {

    public String gerarToken(UserDetails userDetails){
        return Jwts.builder()
                .issuer("WEG")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 300000))
                .signWith(SignatureAlgorithm.NONE, "senha123")
                .subject(userDetails.getUsername())
                .compact();
    }

    public void validarToken(String token){
        getParser().parseSignedClaims(token);
    }

    private JwtParser getParser(){
        return Jwts.parser()
                .setSigningKey("senha123")
                .build();
    }


// getSubject
    public String getUsername(String token){
        return getParser().
                parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
