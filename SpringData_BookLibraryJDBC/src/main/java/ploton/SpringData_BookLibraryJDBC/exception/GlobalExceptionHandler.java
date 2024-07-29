package ploton.SpringData_BookLibraryJDBC.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Error> allEmptyResultDataAccessExceptionHandler(EmptyResultDataAccessException ex) {
        error = new Error("EmptyResultDataAccessException", ex.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> allIllegalArgumentExceptionHandler(IllegalArgumentException ex) {
        error = new Error("IllegalArgumentException", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> allNoSuchElementExceptionHandler(NoSuchElementException ex) {
        error = new Error("NoSuchElementException", ex.toString());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Error> allInvalidDataAccessApiUsageExceptionHandler(InvalidDataAccessApiUsageException ex) {
        error = new Error("InvalidDataAccessApiUsageException", ex.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error {
        private String name;
        private String message;
    }
}
