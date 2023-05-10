package com.flavio.flashfeast.api.exceptionhandler;

import com.flavio.flashfeast.api.exceptionhandler.representation.Fields;
import com.flavio.flashfeast.api.exceptionhandler.representation.Problem;
import com.flavio.flashfeast.domain.exception.DomainException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<Fields> fieldsList = new ArrayList<>();

        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fieldsList.add(new Fields(name, message));
        }
        Problem problem = new Problem();
        problem.setStatus(status.value());
        problem.setOffsetDateTime(dateTimeFormatter.format(OffsetDateTime.now()));
        problem.setTitle("One or more fields are invalid");
        problem.setFieldsRepresentation(fieldsList);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException exception, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem problem = new Problem();
        problem.setStatus(status.value());
        problem.setOffsetDateTime(dateTimeFormatter.format(OffsetDateTime.now()));
        problem.setTitle(exception.getMessage());

        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }
}
