package com.flavio.flashfeast.api.model;

import com.flavio.flashfeast.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CurrentOrderModel {

    private Integer id;
    private String name;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String image;
    private Status status;
    private CompanyModel company;
    private UserModel user;
}
