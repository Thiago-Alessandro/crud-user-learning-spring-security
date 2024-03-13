package net.weg.cruduser;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

import java.security.PublicKey;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AuthFilter authFilter;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        //CSRF -> cross-site request forgery (falsificação de solicitação entre sites)
        //neste caso a csrf esta desativada (nao há proteção para esse ataque)
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/user").hasAuthority(Autorizacao.GET.getAuthority())
                                .requestMatchers(HttpMethod.GET, "/teste").hasAuthority("Get")
//                                .requestMatchers(HttpMethod.GET, "/teste").permitAll()
                                .requestMatchers(HttpMethod.GET, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

//                                .requestMatchers("/user").hasAnyAuthority("Get", "Post")
//                                .requestMatchers("/user").permitAll()
                                .anyRequest().authenticated()
//                                .anyRequest().denyAll()
        );

        http.securityContext((context) -> context.securityContextRepository(securityContextRepository));

        http.logout(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        //stateless -> não há persistencia de sessão, assim que a API envia a response a sessão é terminada
        http.sessionManagement(config -> config.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
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
