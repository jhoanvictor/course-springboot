package com.coursejava.course.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.coursejava.course.services.exceptions.ResourceNotFoundException;

/**
 * 
 * @author jhoan
 * @ControllerAdvice -> captura as exceções que acontecerem para o objeto possa
 *                   fazer um tratamento
 */

@ControllerAdvice
public class ResourceExceptionHandler {

	/**
	 * @ExceptionHandler -> intercepta a requisição que gerou exceção e chama o
	 *                   método para tratar
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

}
