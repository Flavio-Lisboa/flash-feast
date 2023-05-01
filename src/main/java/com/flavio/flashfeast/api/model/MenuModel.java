package com.flavio.flashfeast.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuModel {

    private String name;
    private String category;
    private String description;
    private Integer availableQuantity;
    private BigDecimal price;
    private CompanyModel company;
}
