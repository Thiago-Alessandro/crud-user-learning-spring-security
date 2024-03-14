package net.weg.cruduser.security.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import net.weg.cruduser.model.User;
import net.weg.cruduser.security.model.enums.Autorizacao;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserDetailsEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
//    @Email
    private String username;
    @Column(nullable = false)
    @Length(min = 6)
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Collection<Autorizacao> authorities;
    @NonNull
    @OneToOne(mappedBy = "userDetailsEntity")
    @JsonIgnore
    private User user;

    //atributos obrigatorios : password e username
    //os outros atributos nao precisam ser implementados mas os seus metodos sim

    //caso os atributos na classe estejam em utra lingua como portugues (senha e usuario) ainda devera ser
    //implementado os metodos gets obrigatorios com o nome em ingles obrigado pela interface UserDetails (getPassword) retornando o atributo em portugues (senha)

    //caso nao queira ter os atributos booleanos na entidade não é necessario entretanto necessita a implementação do metodo (por padrao retorna-se true)
    //no caso do getAuthoriteies caso nao se use o atributo ou a funcionalidade de authorities o metodo retorna uma collection de GrantedAuthorities

    //esta classe nao é necessaria. A implementacao de userDetails poderia ser feita no proprio usuario (user)
    //esta classe é feita apenas para se ter uma separação das informações

    //no caso de poucos atributos implementados (como apenas username e password) não há necessidade de uma classe secundaria

    //no caso de poucos atributos do userDetails sendo usados (como password e username) pode-se criar outra classe (apenas um objeto sem ser uma entidade com persistencia) como se fosse um userDetails
    //recebendo o usuario como parametro para setar a senha e o usuario (ou receber os atributos em especificos) apenas para nao ter que se implementar
    //todos os metodos no proprio usuario (apenas etetica e organização), neste caso não há persistencia dos outros dados de userDetails
    // (o que não há complicações visto que esses atributos não estão sendo usados)


}
