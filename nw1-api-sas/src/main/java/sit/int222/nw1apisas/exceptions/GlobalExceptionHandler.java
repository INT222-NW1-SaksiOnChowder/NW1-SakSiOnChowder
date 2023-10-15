package sit.int222.nw1apisas.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import sit.int222.nw1apisas.dtos.announcements.AnnouncementItemDto;
import sit.int222.nw1apisas.dtos.users.CreateUserDto;
import sit.int222.nw1apisas.dtos.users.UpdateUserDto;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUsernameNotExistException(UsernameNotFoundException ex, WebRequest request) {
        String title = "User Not Exist";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), title, request.getDescription(false));
        String errMsg = ex.getMessage();
        errorResponse.addValidationError("username", errMsg);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnAuthorizationException.class})
    public ResponseEntity<ErrorResponse> handleUnAuthorizationException(UnAuthorizationException ex, WebRequest request) {
        String title = "Unauthenticated";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), title, request.getDescription(false));
        String errMsg = ex.getMessage();
        errorResponse.addValidationError("password", errMsg);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({AnnouncementNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoAnnouncementFoundException(AnnouncementNotFoundException ex, WebRequest request) {
        String title = "Announcement item not found"; // You can customize the title as needed
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), title, request.getDescription(false));
        String errMsg = ex.getMessage();
        errorResponse.addValidationError("announcement", errMsg);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AnnouncementBadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(AnnouncementBadRequestException ex, WebRequest request) {
        String title = "Announcement must be Integer";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), title, request.getDescription(false));
        String errMsg = ex.getMessage();
        errorResponse.addValidationError("announcement id", errMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}

