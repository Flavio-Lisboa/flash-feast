package com.flavio.flashfeast.api.model;

import com.flavio.flashfeast.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class FinishedOrderModel {

    private String name;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String image;
    private Status status;
    private Date dateTime;
    private Date completionDateTime;
    private CompanyModel company;
    private UserModel user;
}
