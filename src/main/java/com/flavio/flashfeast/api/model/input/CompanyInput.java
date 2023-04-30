package com.flavio.flashfeast.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyInput {

    private static final int CNPJ_SIZE = 14;

    @NotBlank
    @Size(min = CNPJ_SIZE, max = CNPJ_SIZE)
    @Pattern(regexp = "^\\d+$")
    private String cnpj;
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String name;
    @NotBlank
    @Size(min = 12, max = 50)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;
    @NotBlank
    @Size(min = 8, max = 12)
    private String password;
    @NotBlank
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^\\d+$")
    private String phone;
}
