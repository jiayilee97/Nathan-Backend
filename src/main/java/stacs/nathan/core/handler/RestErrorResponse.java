package stacs.nathan.core.handler;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class RestErrorResponse {

    private HttpStatus status;
    private Map<String, Set<String>> errors;
    private String message;

    public RestErrorResponse(String message, HttpStatus status) {
        setStatus(status);
        setMessage(message);
        setErrors(Collections.emptyMap());
    }

    public RestErrorResponse(String message) {
        setStatus(HttpStatus.BAD_REQUEST);
        setMessage(message);
        setErrors(Collections.emptyMap());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Map<String, Set<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Set<String>> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
