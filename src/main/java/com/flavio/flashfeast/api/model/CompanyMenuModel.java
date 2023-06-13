package com.flavio.flashfeast.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CompanyMenuModel {

    private Integer companyId;
    private String companyName;
    private String companyLogo;
    private List<MenuWithoutCompanyDataModel> menus;
}
