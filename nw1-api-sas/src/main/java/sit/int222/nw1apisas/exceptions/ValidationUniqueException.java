package sit.int222.nw1apisas.exceptions;

import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
public class ValidationUniqueException extends RuntimeException {
    private final String field;
    private final String errorMessage;
}
