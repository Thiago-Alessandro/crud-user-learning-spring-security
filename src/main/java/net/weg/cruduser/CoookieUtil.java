package net.weg.cruduser;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class CoookieUtil {

    //centraliza as questões de cookie

    public Cookie gerarCookieJwt(UserDetails userDetails){
        String token = new JwtUtil().gerarToken(userDetails);
        Cookie cookie = new Cookie("JWT",token);

        //O cookie fica disponivel na rota setada e  qualquer outra apos ela (no caso todas as rotas)
        cookie.setPath("/");
        //duração em segundos do cookie
        cookie.setMaxAge(300);
        return cookie;
    }

    public Cookie getCookie(HttpServletRequest request,
                            String name) throws Exception{
        Cookie cookie = WebUtils.getCookie(request, name);
        if(cookie != null){
            return cookie;
        }
        throw new Exception("Cookie não encontrado");
    }

    //nao era p ser necessario, mas serve para excluir um cookie
    public Cookie gerarCookieNull( ){
//        String token = new JwtUtil().gerarToken(userDetails);
        Cookie cookie = new Cookie("JWT", "");

        //O cookie fica disponivel na rota setada e  qualquer outra apos ela (no caso todas as rotas)
        cookie.setPath("/");
        //duração em segundos do cookie
        cookie.setMaxAge(0);
        return cookie;
    }

}
