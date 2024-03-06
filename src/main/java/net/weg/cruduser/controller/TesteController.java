package net.weg.cruduser.controller;

import lombok.AllArgsConstructor;
import net.weg.cruduser.model.User;
import net.weg.cruduser.model.dto.UserCreateDTO;
import net.weg.cruduser.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/teste")
@AllArgsConstructor
public class TesteController {

    private final UserService userService;

    @GetMapping
//    public Collection<User> teste() {
//        return userService.findAll();
//    }
    public String teste() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        return "Teste " + auth.getName() + "!";
    }

    @PostMapping
    public void cadastroUsuario(@RequestBody UserCreateDTO user) {
        userService.create(user);
    }
}