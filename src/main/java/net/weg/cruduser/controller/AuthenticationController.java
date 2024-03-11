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
@RequestMapping("/authentication")
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

//            UsernamePasswordAuthenticationToken authenticationToken =
            Authentication authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

//          transferido para authFilter (doFilterInternal)
//            //cria contexto novo (vazio) (ja passou pela autenticação pq se nao a autenticacao lanca uma excecao)
//            //sem o contexto de autenticacao a autenticacao não fica salva, exigiria autenticacao em toda requisição
//            //O contexto mantem o usuario ativo, poderia ser substituido por guardar as informacoes dos usuario logados em algum banco de dados
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//
//            //seta como objeto de autenticao o objeto retornado pela autenticacao ja autenticado (que foi setado isAthenticated como true)
//            context.setAuthentication(authentication);
//            securityContextRepository.saveContext(context, request, response);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Cookie cookie = coookieUtil.gerarCookieJwt(userDetails);
            response.addCookie(cookie);

//            SecurityContextHolder.setContext(context);

            return ResponseEntity.ok("Autenticação bem-sucedida");
        } catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }

}
