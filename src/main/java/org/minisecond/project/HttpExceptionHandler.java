package org.minisecond.project;

import java.util.Map;
import java.util.stream.Collectors;

import org.minisecond.project.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> loginValidException(IllegalArgumentException e){
		Map<String, String> map = Util.createMapString();
		return ResponseEntity.badRequest().body(Util.putMsg("msg", e.getMessage(), map));
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> map = Util.createMapString();

	    String errorMessage = ex.getFieldErrors().stream()
        .map(error -> error.getField() + " : " + error.getDefaultMessage())
        .collect(Collectors.joining("\n"));

        return ResponseEntity.badRequest().body(Util.putMsg("msg", errorMessage, map));
    }
	

}
