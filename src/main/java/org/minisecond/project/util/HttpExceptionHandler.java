package org.minisecond.project.util;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> loginValidException(IllegalArgumentException e){
		return ResponseEntity.badRequest().body(Map.of("msg", e.getMessage()));
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

	    String errorMessage = ex.getFieldErrors().stream()
        .map(error -> error.getField() + " : " + error.getDefaultMessage())
        .collect(Collectors.joining("\n"));

        return ResponseEntity.badRequest().body(Map.of("msg", errorMessage));
    }
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Map<String, String>> DBException(SQLException e){
		return ResponseEntity.badRequest().body(Map.of("msg", e.getMessage()));
	}
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> customError(CustomException e) {
    	return ResponseEntity.badRequest().body(Map.of("msg", e.getMessage()));
    }
}
