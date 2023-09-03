package sit.int222.nw1apisas.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationAndParsingExceptions(Exception ex, WebRequest request) {
        String title = "Announcement attributes validation failed";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), title, request.getDescription(false));

        if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            for (ObjectError field : bindingResult.getAllErrors()) {
                String fieldName = field.getCode();
                if (field instanceof FieldError) {
                    fieldName = ((FieldError) field).getField();
                }
                errorResponse.addValidationError(fieldName, field.getDefaultMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}

