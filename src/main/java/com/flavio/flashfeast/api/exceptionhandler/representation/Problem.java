package com.flavio.flashfeast.api.exceptionhandler.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;
    private String offsetDateTime;
    private String title;
    private List<Fields> fieldsRepresentation;
}
