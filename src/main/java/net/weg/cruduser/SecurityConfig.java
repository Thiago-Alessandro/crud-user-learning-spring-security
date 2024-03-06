package net.weg.cruduser;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.security.PublicKey;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        //CSRF -> cross-site request forgery (falsificação de solicitação entre sites)
        //neste caso a csrf esta desativada (nao há proteção para esse ataque)
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/user").hasAuthority("Get")
                                .requestMatchers("/user").hasAnyAuthority("Get", "Post")
//                                .requestMatchers("/user").permitAll()
                                .anyRequest().authenticated()
//                                .anyRequest().denyAll()
        );

        //O contexto de segurança serve para manter a sessao do usuario autenticado
        //por padrao armazenado em memoria
        http.securityContext((context) -> context.securityContextRepository(securityContextRepository));

        http.formLogin(Customizer.withDefaults());
//        http.logout(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }


    //  @Bean
//    public InMemoryUserDetailsService inMemoryUserDetailsManager(){
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//
//      UserDetails user = User.withDefaultPasswordEncoder()
//              .username("mi72")
//              .password("M!7dois")
//              .build();
////        UserDetails user2 = User.withDefaultPasswordEncoder()
////                .username("mi72")
////                .password("M!7dois")
////                .build();
//
////        UserDetails user = User.builder()
////                .username("mi72")
////                .password("M!7dois")
////                .passwordEncoder(new )
////                .build();
//      return new InMemoryUserDetailsManager();
//    }

}
