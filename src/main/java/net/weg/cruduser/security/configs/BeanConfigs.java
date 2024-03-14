package net.weg.cruduser.security.configs;

import lombok.AllArgsConstructor;
import net.weg.cruduser.security.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BeanConfigs {

    private final AuthenticationService authenticationService;

    @Bean
    public CorsConfigurationSource corsConfig(){
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        //seta credentials true para poder pegar os cookies
        corsConfig.setAllowCredentials(true);

        corsConfig.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource corsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfig);

        return corsConfigurationSource;
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(authenticationService);
        return new ProviderManager(provider);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
////        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
//    }

//    @Autowired
//    public void configureGlobal(
//            AuthenticationManagerBuilder auth,
//            AuthenticationService authenticationService
//    ) throws Exception {
//        auth
//                .userDetailsService(authenticationService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

//    @Bean
//    public UserDetailsService userDetailsService(AuthenticationService authenticationService){
//        return authenticationService;
//    }



}
