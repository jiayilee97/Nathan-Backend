package stacs.nathan.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import stacs.nathan.Utils.response.RestErrorResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        logger.error("Access Denied exception {}", ex);
        return new ResponseEntity<>(new RestErrorResponse("Access Denied"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({Exception.class, java.lang.NullPointerException.class})
    protected ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error("Internal Server Error, unhandled exception {}", ex);
        return new ResponseEntity<>(new RestErrorResponse("Error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IOException.class})
    protected ResponseEntity<Object> handleInternalError(Exception ex, WebRequest request) {
        logger.error("Internal Server Error, unhandled exception {}", ex);
        return new ResponseEntity<>(new RestErrorResponse("Error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
