package com.flavio.flashfeast.api.exceptionhandler.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;
    private String offsetDateTime;
    private String title;
    private List<Fields> fieldsRepresentation;

    public Problem(Integer status, String offsetDateTime, String title) {
        this.status = status;
        this.offsetDateTime = offsetDateTime;
        this.title = title;
    }
}
