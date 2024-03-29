package net.weg.cruduser.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.cruduser.security.service.AuthenticationService;
import net.weg.cruduser.security.utils.CoookieUtil;
import net.weg.cruduser.security.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final CoookieUtil coookieUtil = new CoookieUtil();
    private final JwtUtil jwtUtil = new JwtUtil();

    private final SecurityContextRepository securityContextRepository;

    //depende do que for colocado como subject bno token, use esta classe se for um atributo autenticado (como username, email, cpf que esteja presentena userDetails )
    private AuthenticationService authenticationService;

    //executara em cada requisicao da API
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(!rotaPublica(request)){
            //Busca e validação do token
            Cookie cookie;
            try {
                cookie = coookieUtil.getCookie(request, "JWT");
            } catch (Exception e) {
                response.setStatus(401);
                return;
            }

            String token = cookie.getValue();
            String username = jwtUtil.getUsername(token);

            //criação do usuario autenticado
            UserDetails userDetails = authenticationService.loadUserByUsername(username);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());

            //salvamento do usuario austenticado no Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            //seta como objeto de autenticao o objeto retornado pela autenticacao ja autenticado (que foi setado isAthenticated como true)
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);

            // Renovação do JWT e Cookie
            Cookie cookieRenovado = coookieUtil.gerarCookieJwt(userDetails);
            response.addCookie(cookieRenovado);
        }
        //Continuação da requisição
        filterChain.doFilter(request,response);
    }

    //definir aqui todas as rotas publicas (permitAll no authFilter)
    private boolean rotaPublica(HttpServletRequest request){
        return request.getRequestURI().equals("/auth/login");
//                && (request.getMethod().equals("GET") || request.getMethod().equals("POST"));
    }

}
