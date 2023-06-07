package com.flavio.flashfeast.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationResponseModel {

    private String token;
}
