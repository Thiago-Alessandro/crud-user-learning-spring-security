package net.weg.cruduser.configs;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.cruduser.model.User;
import net.weg.cruduser.security.model.entity.UserDetailsEntity;
import net.weg.cruduser.repository.UserRepository;
import net.weg.cruduser.security.model.enums.Autorizacao;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DatabaseConfig {

    private final UserRepository userRepository;

    @PostConstruct
    public void init(){
        try {
            userRepository.findByUserDetailsEntity_Username("teste").get();
        } catch (Exception e) {
            User user = new User();
            user.setName("Teste");
            user.setUserDetailsEntity(
                    UserDetailsEntity.builder()
                            .user(user)
                            .enabled(true)
                            .accountNonExpired(true)
                            .accountNonLocked(true)
                            .credentialsNonExpired(true)
                            .username("teste")
                            .password(new BCryptPasswordEncoder().encode("teste123"))
                            .authorities(List.of(Autorizacao.GET, Autorizacao.POST))
                            .build()
            );
            userRepository.save(user);
        }
    }

}
