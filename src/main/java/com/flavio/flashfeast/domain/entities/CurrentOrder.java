package com.flavio.flashfeast.domain.entities;

import com.flavio.flashfeast.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "current_order")
public class CurrentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
    private String image;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "date_time")
    private Date dateTime;
    @Column(name = "expiration_time")
    private Long expirationTime;

    @ManyToOne
    private Company company;

    @ManyToOne
    private User user;

    @ManyToOne
    private Menu menu;
}
