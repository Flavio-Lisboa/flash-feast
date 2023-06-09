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
@Table(name = "user_location")
public class UserLocation {

    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "zip_code")
    private String zipcode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;

    @Column(name = "house_number")
    private String houseNumber;
}
