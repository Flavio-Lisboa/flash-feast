package com.flavio.flashfeast.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyInput {

    private String cnpj;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String logo;
}
