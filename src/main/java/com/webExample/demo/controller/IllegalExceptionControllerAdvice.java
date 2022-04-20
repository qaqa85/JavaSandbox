package com.webExample.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

class IllegalExceptionControllerAdvice {
    @ControllerAdvice(annotations = IllegalExceptionProcessing.class)
    class IllegalExceptionsControllerAdvice {
        @ExceptionHandler(IllegalArgumentException.class)
        ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

        @ExceptionHandler(IllegalStateException.class)
        ResponseEntity<String> handleIllegalState(IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
