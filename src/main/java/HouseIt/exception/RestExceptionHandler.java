package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    protected ResponseEntity<java.lang.Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Missing Path Variable Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, String.format("404 Not Found, oh no! Invalid URL request for %s", request.getContextPath()), e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Missing Servlet Request Parameter Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("400 Bad Request, oh no! Parameter %s is missing", e.getParameterName()), e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Http Message Not Readable Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500 Internal Server Error, oh no! Could not write JSON", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Http Message Not Readable Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "400 Not Found Error, oh no!", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Method Argument Not Valid Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "401 Bad Request, oh no!. Server cannot process your request...", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        logger.error("Data Integrity Violation Exception: ", e);
        ErrorResponse response = null;

        if (e.getCause() instanceof ConstraintViolationException) {
            response = new ErrorResponse(HttpStatus.CONFLICT, "500 Internal Server Error, oh no! This action conflicts with the database...", e);
        } else {
            response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500 Internal Server Error, oh no! This action conflicts with the database...", e);
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({MyEntityNotFoundException.class})
    protected ResponseEntity<Object> handleMyResourceNotFound(MyEntityNotFoundException e) {
        logger.error("My Resource Not Found Exception ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, "404 Not Found, oh no!", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        logger.error("Entity Not Found Exception ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, "404 Not Found, oh no!", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        logger.error("Access is denied ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "401 Unauthorized, oh no! You don't have access to this...", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        logger.error("Access is denied ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "401 Unauthorized, oh no! You don't have access to this...", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    protected ResponseEntity<Object> handleInsufficientAuthenticationException(InsufficientAuthenticationException e, WebRequest request) {
        logger.error("Access is denied ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "401 Unauthorized, oh no! You don't have access to this...", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInternal(RuntimeException e, WebRequest request) {
        logger.error("Runtime Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500 Internal Server Error, oh no! Something bad happened. Please try again later.", e);

        return new ResponseEntity<Object>(response, response.getStatus());
    }

    @ExceptionHandler({PasswordsDontMatchException.class})
    public ResponseEntity<Object> handlePasswordsDontMatch(RuntimeException e, WebRequest request) {
        logger.error("Passwords don't match exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Oh no! Your old password doesn't match with your current password...", e);

        return new ResponseEntity<Object>(response, response.getStatus());
    }

}