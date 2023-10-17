package sit.int222.nw1apisas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter
@AllArgsConstructor
public class ValidationUniqueException extends RuntimeException {
    private final String field;
    private final String errorMessage;
}
