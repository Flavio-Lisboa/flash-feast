package com.flavio.flashfeast.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "company_location")
public class CompanyLocation {

    @Id
    @Column(name = "company_id")
    private Integer companyId;
    @Column(name = "zip_code")
    private String zipcode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;

    @Column(name = "company_number")
    private String companyNumber;
}
