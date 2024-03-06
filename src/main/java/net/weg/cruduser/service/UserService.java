package net.weg.cruduser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.weg.cruduser.model.Archive;
import net.weg.cruduser.model.User;
import net.weg.cruduser.model.dto.UserCreateDTO;
import net.weg.cruduser.model.dto.UserUpdateDTO;
import net.weg.cruduser.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public User create(UserCreateDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return userRepository.save(user);
    }

    public User save(String userJSON, MultipartFile picture) {
        User user;
        try {
            user = objectMapper.readValue(userJSON, User.class);
            user.setPicture(picture);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return userRepository.save(user);
    }

    public User update(UserUpdateDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return userRepository.save(user);
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    public User findOne(Integer id){
        return userRepository.findById(id).get();
    }

    public Collection<User> findAll(){
        return userRepository.findAll();
    }

    public User patchPassword(Integer id, String password){
        User user = findOne(id);
        if(user != null){
            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    public User patchAccountStatus(Integer id, boolean isActive){
        User user = findOne(id);
        user.setActive(isActive);
        return userRepository.save(user);
    }

    public User patchPicuture(Integer id, MultipartFile picture){
        User user = findOne(id);
        user.setPicture(picture);
        return userRepository.save(user);
    }

}
