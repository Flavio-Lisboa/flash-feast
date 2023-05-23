package com.flavio.flashfeast.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentOrderInput {

    @NotNull
    private Integer quantity;
}
