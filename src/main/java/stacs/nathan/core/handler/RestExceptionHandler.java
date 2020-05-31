package stacs.nathan.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        LOGGER.error("Access Denied exception {}", ex);
        return new ResponseEntity<>(new RestErrorResponse("Access is Denied", HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        LOGGER.error("BadRequestException: {}", ex);
        return new ResponseEntity<>(new RestErrorResponse("Error: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServerErrorException.class, Exception.class})
    protected ResponseEntity<Object> handleServerErrorException(Exception ex) {
        LOGGER.error("Exception {}", ex);
        return new ResponseEntity<>(new RestErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
