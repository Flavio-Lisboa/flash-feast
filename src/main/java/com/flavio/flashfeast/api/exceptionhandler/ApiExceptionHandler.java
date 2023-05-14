package com.flavio.flashfeast.api.exceptionhandler;

import com.flavio.flashfeast.api.exceptionhandler.representation.Fields;
import com.flavio.flashfeast.api.exceptionhandler.representation.Problem;
import com.flavio.flashfeast.domain.exception.AlreadyExistsException;
import com.flavio.flashfeast.domain.exception.DomainException;
import com.flavio.flashfeast.domain.exception.NotFoundException;
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
        Problem problem = buildProblem(status.value(), "One or more fields are invalid", fieldsList);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException exception, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem problem = buildProblem(status.value(), exception.getMessage());
        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Problem problem = buildProblem(status.value(), exception.getMessage());
        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException exception, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Problem problem = buildProblem(status.value(), exception.getMessage());
        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    private Problem buildProblem(Integer status, String title) {
        return new Problem(status, dateTimeFormatter.format(OffsetDateTime.now()), title);
    }

    private Problem buildProblem(Integer status, String title, List<Fields> fieldsList) {
        return new Problem(status, dateTimeFormatter.format(OffsetDateTime.now()), title, fieldsList);
    }
}
