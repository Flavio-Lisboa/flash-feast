package com.flavio.flashfeast.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLocationModel {

    private Integer userId;
    private String zipcode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String houseNumber;
}
