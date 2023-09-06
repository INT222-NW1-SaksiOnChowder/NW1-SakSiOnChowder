package sit.int222.nw1apisas.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.dtos.announcements.AnnouncementItemDto;
import sit.int222.nw1apisas.dtos.users.CreateUserDto;
import sit.int222.nw1apisas.dtos.users.UpdateUserDto;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationAndParsingExceptions(Exception ex, WebRequest request) {
        String title = determineTitle(ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), title, request.getDescription(false));
        BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        for (ObjectError field : bindingResult.getAllErrors()) {
            String fieldName = field.getCode();
            if (field instanceof FieldError) {
                fieldName = ((FieldError) field).getField();
            }
            errorResponse.addValidationError(fieldName, field.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private String determineTitle(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) ex;
//          ไปเอา object dto มา
            Object target = validationException.getTarget();
            if (target instanceof AnnouncementItemDto) {
                return "Announcement attributes validation failed";
            } else if (target instanceof CreateUserDto || target instanceof UpdateUserDto) {
                return "User attributes validation failed";
            }
        }
        return "Validation failed";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationUniqueException.class})
    public ResponseEntity<ErrorResponse> handleValidationUniqueException(ValidationUniqueException ex, WebRequest request) {
        String title = "User attributes validation failed"; // You can customize the title as needed
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), title, request.getDescription(false));
        errorResponse.addValidationError(ex.getField(), ex.getErrorMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}

