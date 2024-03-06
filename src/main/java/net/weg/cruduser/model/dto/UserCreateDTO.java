package net.weg.cruduser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDTO {

    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private Integer age;
}
