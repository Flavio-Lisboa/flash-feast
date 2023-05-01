package com.flavio.flashfeast.api.exceptionhandler.representation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Problem {

    private Integer status;
    private LocalDateTime localDateTime;
    private String title;
    private List<Fields> fieldsRepresentation;
}
