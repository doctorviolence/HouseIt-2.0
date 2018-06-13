package HouseIt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Missing and/or wrong information, please check data input.")
public class MissingInformationException extends RuntimeException {

}
