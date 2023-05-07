package com.flavio.flashfeast.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyLocationInput {

    private static final int ZIPCODE_SIZE = 8;

    @NotBlank
    @Size(min = ZIPCODE_SIZE, max = ZIPCODE_SIZE)
    @Pattern(regexp = "^\\d+$")
    private String zipcode;
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String state;
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String city;
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String neighborhood;
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String street;
    @NotBlank
    @Size(min = 1, max = 10)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String companyNumber;
}
