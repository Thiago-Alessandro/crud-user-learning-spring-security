package net.weg.cruduser.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.cruduser.CoookieUtil;
import net.weg.cruduser.JwtUtil;
import net.weg.cruduser.model.UserLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil = new JwtUtil();
    private  final CoookieUtil coookieUtil = new CoookieUtil();

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(
            @RequestBody UserLogin user,
            HttpServletRequest request,
            HttpServletResponse response){
        try {

            Authentication authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Cookie cookie = coookieUtil.gerarCookieJwt(userDetails);
            response.addCookie(cookie);

            return ResponseEntity.ok("Autenticação bem-sucedida");
        } catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {
        response.addCookie(coookieUtil.gerarCookieNull());
//        Cookie cookie = coookieUtil.getCookie(request, "JWT");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
    }

}
