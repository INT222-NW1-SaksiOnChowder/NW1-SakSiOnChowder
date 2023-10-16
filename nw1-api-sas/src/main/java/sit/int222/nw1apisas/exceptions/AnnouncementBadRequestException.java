package sit.int222.nw1apisas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AnnouncementBadRequestException extends RuntimeException {
    public AnnouncementBadRequestException(String message) {
        super(message);
    }
}