package com.project.api.exception;

import com.project.api.model.base.ApiResponse;
import com.project.api.model.base.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop through each validation error
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()) // Get the custom message
        );

        // Customize your JSON structure here
        Map<String, Object> response = new HashMap<>();
        response.put("status", "400");
        response.put("error", "Bad Request");
        response.put("message", "Validation failed");
        response.put("errors", errors);  // Add the field errors

        return ResponseEntity.badRequest().body(response);
    }


    // Handle other exceptions generically
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "500");
        response.put("error", "Internal Server Error");
        response.put("message", "An unexpected error occurred.");
        response.put("errors", new HashMap<String, String>());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle resource not found errors
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse response = new ApiResponse("Resource not found", 404, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryNameExistsException(CategoryNameAlreadyExistsException ex) {
        // Create the custom error response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "400");
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage()); // This will be "Category name already exists!"
        response.put("errors", new HashMap<String, String>()); // No specific field error here

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
