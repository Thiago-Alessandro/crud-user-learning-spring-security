package net.weg.cruduser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;
    private String email;
    private String password;
    private Integer age;
    @OneToOne(cascade = CascadeType.ALL)
    private Archive picture;
    private boolean isActive;

    //n sei rever isso aqui
    @OneToOne( cascade = CascadeType.ALL)
    private UserDetailsEntity userDetailsEntity;


    public void setPicture(MultipartFile picture) {
        Archive archive = new Archive();
        try {
            archive.setData(picture.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        archive.setName(picture.getOriginalFilename());
        archive.setType(picture.getContentType());
        this.picture = archive;
    }
}
