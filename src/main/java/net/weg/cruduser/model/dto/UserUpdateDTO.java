package net.weg.cruduser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDTO {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private Integer age;
}
