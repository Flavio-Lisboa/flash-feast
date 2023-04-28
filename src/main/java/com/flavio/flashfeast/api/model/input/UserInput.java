package com.flavio.flashfeast.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

    private static final int CPF_SIZE = 11;

    @NotBlank
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String lastName;
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
    @NotBlank
    @Size(min = CPF_SIZE, max = CPF_SIZE)
    @Pattern(regexp = "^\\d+$")
    private String cpf;
}
