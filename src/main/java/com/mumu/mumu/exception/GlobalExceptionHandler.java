//package com.mumu.mumu.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import jakarta.servlet.http.HttpServletRequest;
//
//import java.time.LocalDateTime;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
//        List<String> errors = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, request.getRequestURI());
//    }
//
//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<Object> handleBindException(BindException e, HttpServletRequest request) {
//        List<String> errors = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, request.getRequestURI());
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleBadRequest(IllegalArgumentException e, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleInternalError(Exception e, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.", request.getRequestURI());
//    }
//
//    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, Object message, String path) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("status", status.value());
//        body.put("error", status.getReasonPhrase());
//        body.put("message", message);
//        body.put("path", path);
//
//        return new ResponseEntity<>(body, status);
//    }
//}
