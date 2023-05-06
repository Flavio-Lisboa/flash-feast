package com.flavio.flashfeast.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLocationInput {

    private String state;
    private String city;
    private String street;
    private String houseNumber;
}
