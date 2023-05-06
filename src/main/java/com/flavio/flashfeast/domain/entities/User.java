package com.flavio.flashfeast.domain.entities;

import com.flavio.flashfeast.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserLocation userLocation;
}
