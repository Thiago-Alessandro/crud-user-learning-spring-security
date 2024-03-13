package net.weg.cruduser;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

public class JwtUtil {

    private final SecretKey key;

    public  JwtUtil(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("senha123");
        this.key = Keys.hmacShaKeyFor(password.getBytes());
    }

    public String gerarToken(UserDetails userDetails){
        return Jwts.builder()
                .issuer("WEG")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 300000))
//                .encryptWith()
                .signWith(this.key, Jwts.SIG.HS256)
                .subject(userDetails.getUsername())
                .compact();
    }

    private Jws<Claims> validarToken(String token){
        return getParser().parseSignedClaims(token);
    }

    private JwtParser getParser(){
        return Jwts.parser()
                .verifyWith(this.key)
//                .setSigningKey("senha123")
                .build();
    }


// getSubject
    public String getUsername(String token){
        return validarToken(token)
                .getPayload()
                .getSubject();
    }

}
