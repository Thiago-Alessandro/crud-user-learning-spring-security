package net.weg.cruduser.controller;

import lombok.AllArgsConstructor;
import net.weg.cruduser.model.User;
import net.weg.cruduser.model.dto.UserCreateDTO;
import net.weg.cruduser.model.dto.UserUpdateDTO;
import net.weg.cruduser.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping("/full")
    private User create(@RequestParam String user,@RequestParam MultipartFile picture){
        return userService.save(user, picture);
    }

    @PostMapping()
    private User create(@RequestBody UserCreateDTO user){
        return userService.create(user);
    }

    @PutMapping("/full")
    private User update(@RequestParam String user, @RequestParam MultipartFile picture){
        return userService.save(user, picture);
    }

    @PutMapping()
    private User update(@RequestBody UserUpdateDTO user){
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id){
        userService.delete(id);
    }

    @GetMapping()
    private Collection<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    private User findOne(@PathVariable Integer id){
        return userService.findOne(id);
    }

    @PatchMapping("/password/{id}")
    private User patchPassword(@PathVariable Integer id, @RequestBody String password){
        return userService.patchPassword(id, password);
    }

    @PatchMapping("/picture/{id}")
    private User patchPicture(@PathVariable Integer id, @RequestParam MultipartFile archive) {
        return userService.patchPicuture(id, archive);
    }

    @PatchMapping("/active/{id}")
    private User patchAccountStatus(@PathVariable Integer id, @RequestBody boolean isActive){
        return userService.patchAccountStatus(id, isActive);
    }

}
