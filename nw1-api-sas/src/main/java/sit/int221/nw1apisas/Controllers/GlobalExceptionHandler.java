package sit.int221.nw1apisas.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sit.int221.nw1apisas.Exceptions.BadRequestException;
import sit.int221.nw1apisas.Exceptions.ItemNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseStatusException handleItemNotFoundException (ItemNotFoundException exception) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseStatusException handleBadRequestException (BadRequestException exception) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseStatusException handleNumberFormatException (RuntimeException exception) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number " + exception.getMessage());
    }


    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseStatusException handleNullPointerException (RuntimeException exception) {
        return new ResponseStatusException(HttpStatus.CONFLICT, "Invalid number " + exception.getMessage());
    }

}
