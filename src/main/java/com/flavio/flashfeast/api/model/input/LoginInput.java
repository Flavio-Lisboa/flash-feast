package com.flavio.flashfeast.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInput {

    private String email;
    private String password;
}
