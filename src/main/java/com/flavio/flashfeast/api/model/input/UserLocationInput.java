package com.flavio.flashfeast.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLocationInput {

    private String zipcode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String houseNumber;
}
