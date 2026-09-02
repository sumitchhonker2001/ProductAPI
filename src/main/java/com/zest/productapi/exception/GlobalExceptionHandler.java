package com.zest.productapi.exception;
import jakarta.servlet.http.HttpServletRequest; import org.springframework.http.*; import org.springframework.validation.FieldError; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
 private ResponseEntity<ErrorResponse> build(HttpStatus s,String msg,String path,List<String> d){return ResponseEntity.status(s).body(new ErrorResponse(Instant.now(),s.value(),s.getReasonPhrase(),msg,path,d));}
 @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException e,HttpServletRequest r){return build(HttpStatus.NOT_FOUND,e.getMessage(),r.getRequestURI(),List.of());}
 @ExceptionHandler({ApiException.class,IllegalArgumentException.class}) ResponseEntity<ErrorResponse> bad(RuntimeException e,HttpServletRequest r){return build(HttpStatus.BAD_REQUEST,e.getMessage(),r.getRequestURI(),List.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e,HttpServletRequest r){List<String> d=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).toList();return build(HttpStatus.BAD_REQUEST,"Validation failed",r.getRequestURI(),d);}
 @ExceptionHandler(Exception.class) ResponseEntity<ErrorResponse> other(Exception e,HttpServletRequest r){return build(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected error",r.getRequestURI(),List.of());}
}
