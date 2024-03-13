package net.weg.cruduser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

public class JwtUtil {

//    private final SecretKey key;

//    public  JwtUtil(){
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        String password = encoder.encode("senha123");
//        this.key = Keys.hmacShaKeyFor(password.getBytes());
//    }

    public String gerarToken(UserDetails userDetails){
        Algorithm algorithm = Algorithm.HMAC256("senha123");
        return JWT.create().withIssuer("WEG")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String getUsername(String token){
        return JWT.decode(token).getSubject();
    }


//    private Jws<Claims> validarToken(String token){
//        return getParser().parseSignedClaims(token);
//    }
//
//    private JwtParser getParser(){
//        return JWT.decode().parser()
//                .verifyWith(this.key)
////                .setSigningKey("senha123")
//                .build();
//    }


// getSubject

}
