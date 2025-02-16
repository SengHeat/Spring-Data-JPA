package com.project.api.exception;

import com.project.api.model.base.ApiResponse;
import com.project.api.model.base.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<String> invalidFields = new ArrayList<>();

        // Loop through each validation error and capture the field name with the custom message
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage()); // Get the custom message
            invalidFields.add(error.getField()); // Capture the field that failed validation
        });

        // Create a dynamic message based on invalid fields
        String dynamicMessage = "Validation failed for the following field(s): " + String.join(", ", invalidFields);

        // Create the error response object
        ErrorResponse errorResponse = new ErrorResponse(
                400, // status code
                "Bad Request", // error title
                dynamicMessage, // dynamic message
                errors, // specific field errors
                request.getRequestURI() // path of the request
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        // Create an ErrorResponse with the necessary details
        ErrorResponse errorResponse = new ErrorResponse(
                500, // status code
                "Internal Server Error", // error type
                ex.getMessage(), // generic message
                new HashMap<>(), // no specific validation errors
                request.getRequestURI() // the URI path of the request
        );

        // Add the exception message to the error response (optional)
        errorResponse.setMessage(ex.getMessage());

        // Return the error response as a ResponseEntity with INTERNAL_SERVER_ERROR status
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle resource not found errors
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        // Create an ErrorResponse with the necessary details
        ErrorResponse errorResponse = new ErrorResponse(
                404, // status code
                "Not Found", // error type
                ex.getMessage(), // detailed message from the exception
                new HashMap<>(),
                request.getRequestURI()// the URI path of the request that caused the exception
        );

        // Return the error response with NOT_FOUND status
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleExistsException(AlreadyExistsException ex, HttpServletRequest request) {
        // Prepare the custom error response
        Map<String, String> errors = new HashMap<>();
        // You can populate errors with specific details if needed, such as field names

        // Prepare the ErrorResponse object
        ErrorResponse response = new ErrorResponse(
                409,   // status at the top
                "Conflict",
                ex.getMessage(), // Dynamic message e.g., "Category name already exists!"
                errors, // Include errors map (could be empty or specific)
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        // Prepare the custom error response
        Map<String, String> errors = new HashMap<>();
        // You can populate errors with specific details if needed, such as field names

        // Prepare the ErrorResponse object
        ErrorResponse response = new ErrorResponse(
                404,   // status at the top
                "Not Found",
                ex.getMessage(), // Dynamic message e.g., "Category name already exists!"
                errors, // Include errors map (could be empty or specific)
                request.getRequestURI()
        );

        // Return the response with BAD_REQUEST (400) status
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(InvalidIdFormatException.class)
    public ResponseEntity<ApiResponse> handleInvalidFormatException(InvalidIdFormatException ex, HttpServletRequest request) {
        String message = "Invalid input: '" + ex.getMessage() + "'. Expected type: Expected a valid numeric value.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(message, HttpStatus.BAD_REQUEST.value(), null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid input: '" + ex.getValue() + "'. Expected type: " + ex.getRequiredType().getSimpleName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(message, HttpStatus.BAD_REQUEST.value(), null));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException ex, HttpServletRequest request) {
        // Create a detailed message for the error
        String message = "Invalid input: '" + ex.getMessage() + "'. Expected a valid numeric value.";

        Map<String, String> errors = new HashMap<>();
        // You can populate errors with specific details if needed, such as field names

        // Prepare the ErrorResponse object
        ErrorResponse response = new ErrorResponse(
                400,   // status at the top
                "Bad Request",
                message, // Dynamic message e.g., "Category name already exists!"
                errors, // Include errors map (could be empty or specific)
                request.getRequestURI()
        );

        // Return a BAD_REQUEST response with the message and status code
        return ResponseEntity.status(400).body(response);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "Request method '" + ex.getMethod() + "' is not supported. Supported methods: " + ex.getSupportedHttpMethods();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiResponse(message, HttpStatus.METHOD_NOT_ALLOWED.value(), null));
    }

    // Handle missing or invalid routes
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ApiResponse response = new ApiResponse("No static resource for the path: " + ex.getRequestURL(), HttpStatus.NOT_FOUND.value(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
