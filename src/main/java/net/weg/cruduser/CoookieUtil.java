package net.weg.cruduser;

import jakarta.servlet.http.Cookie;
import org.springframework.security.core.userdetails.UserDetails;

public class CoookieUtil {

    public Cookie gerarCookie(UserDetails userDetails){
        String token = new JwtUtil().gerarToken(userDetails);
        Cookie cookie = new Cookie("JWT",token);
        cookie.setPath("/");
        cookie.setMaxAge(300);
        return cookie;
    }

}
