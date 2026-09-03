package spacenes._stproject.core.utilities.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import spacenes._stproject.core.utilities.results.ErrorDataResult;
import spacenes._stproject.core.utilities.results.ErrorResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ErrorResult> handleTaskNotFound(TaskNotFoundException ex) {
		
		ErrorResult result = new ErrorResult(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDataResult<Map<String, String>>> handleValidationErrors (MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<>(); 
		
		ex.getBindingResult()
		      .getFieldErrors()
		      .forEach(error -> 
		             errors.put(error.getField(), error.getDefaultMessage()));
		
		ErrorDataResult<Map<String, String>> result = new ErrorDataResult<>(errors, "Validation Failed");
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(result);
	}
} 
  