package com.flavio.flashfeast.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String cpf;
}
