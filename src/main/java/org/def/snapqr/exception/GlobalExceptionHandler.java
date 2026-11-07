package org.def.snapqr.exception;

import org.def.snapqr.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException (
            MethodArgumentNotValidException ex
    ) {

        String message = Objects.requireNonNull(ex.getBindingResult()
                        .getFieldError())
                .getDefaultMessage();

        if(message == null) {
            message = "Invalid request";
        }

        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException (Exception ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occured"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = QrGenerationException.class)
    public ResponseEntity<ApiErrorResponse> handleQRGenerationException (
            QrGenerationException ex
    ) {

        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Failed to generate QR code"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
