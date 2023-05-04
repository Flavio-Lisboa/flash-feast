package com.flavio.flashfeast.api.model.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuInput {

    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String name;
    @NotBlank
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String category;
    @NotBlank
    @Size(min = 1, max = 200)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String description;
    @NotNull
    private Integer availableQuantity;
    @NotNull
    @DecimalMin("1.00")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;
}
