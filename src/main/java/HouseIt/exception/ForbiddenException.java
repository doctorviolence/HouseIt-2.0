package HouseIt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You don't have access to this.")
public class ForbiddenException extends RuntimeException {
}
