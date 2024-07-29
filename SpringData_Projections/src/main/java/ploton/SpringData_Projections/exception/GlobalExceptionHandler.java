package ploton.SpringData_Projections.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Error error;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> allExceptionHandler(Exception ex) {
        error = new Error("InternalServerError", ex.toString());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateEntityException.class)
    public ResponseEntity<Error> validateEntityExceptionHandler(ValidateEntityException ex) {
        error = new Error("ValidateEntityException", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> noSuchElementExceptionHandler(NoSuchElementException ex) {
        error = new Error("NoSUchElementException", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error {
        private String name;
        private String message;
    }
}

