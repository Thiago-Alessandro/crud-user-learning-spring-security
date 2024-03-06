package net.weg.cruduser;

import lombok.AllArgsConstructor;
import net.weg.cruduser.model.User;
import net.weg.cruduser.model.UserDetailsEntity;
import net.weg.cruduser.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserDetailsEntity_Username(username);
        if(optionalUser.isPresent()){
            return optionalUser.get().getUserDetailsEntity();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}
