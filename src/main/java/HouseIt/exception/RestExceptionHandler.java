package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    public RestExceptionHandler() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Http Message Not Readable Exception: ", e.getCause());
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Method Argument Not Valid Exception: ", e.getCause());
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation error", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        logger.error("Data Integrity Violation Exception: ", e.getCause());
        ErrorResponse response = null;

        if (e.getCause() instanceof ConstraintViolationException) {
            response = new ErrorResponse(HttpStatus.CONFLICT, "Database error", e.getCause());
        } else {
            response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({MyEntityNotFoundException.class})
    protected ResponseEntity<Object> handleMyResourceNotFound(MyEntityNotFoundException e) {
        logger.error("My Resource Not Found Exception ", e.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        logger.error("Entity Not Found Exception ", e.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, "Entity not found", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInternal(RuntimeException e, WebRequest request) {
        logger.error("Runtime Exception: ", e.getCause());
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);

        return new ResponseEntity<Object>(response, response.getStatus());
    }

}