package crud.topic.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handlerUsernameNotFoundException(UsernameNotFoundException exception) {
        return new ResponseEntity<>(
                errorDetails(exception.getMessage(), HttpStatus.NOT_FOUND, timestamp()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handlerBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(
                errorDetails(exception.getMessage(), HttpStatus.UNAUTHORIZED, timestamp()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException exception) {
        return new ResponseEntity<>(
                errorDetails(exception.getMessage(), HttpStatus.CONFLICT, timestamp()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(
                errorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST, timestamp()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(
                errorDetails(exception.getMessage(), HttpStatus.UNAUTHORIZED, timestamp()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerInvalidArguments(MethodArgumentNotValidException exception) {

        FieldError fieldError = exception.getBindingResult().getFieldError();
        Map<String, Object> errorDetails = new HashMap<>();

        if (fieldError != null) {
            errorDetails.put("message", fieldError.getDefaultMessage());
            errorDetails.put("field", Objects.requireNonNull(exception.getBindingResult().getFieldError()).getField());
            errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
            errorDetails.put("timestamp", timestamp());
        }

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    private String timestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        return dateFormat.format(new Date());
    }

    private Map<String, Object> errorDetails(String message, HttpStatus status, String timestamp) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", message);
        errors.put("status", status.value());
        errors.put("timestamp", timestamp);

        return errors;
    }
}
