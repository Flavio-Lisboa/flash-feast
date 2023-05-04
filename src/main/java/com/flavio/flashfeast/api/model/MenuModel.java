package com.flavio.flashfeast.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuModel {

    private Integer id;
    private String name;
    private String category;
    private String description;
    private Integer availableQuantity;
    private BigDecimal price;
    private String image;
    private CompanyModel company;
}
