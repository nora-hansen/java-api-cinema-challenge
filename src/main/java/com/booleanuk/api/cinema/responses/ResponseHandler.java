package com.booleanuk.api.cinema.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(
            String status,
            HttpStatus statusCode,
            Object object) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("data", object);

        return new ResponseEntity<>(responseMap, statusCode);
    }

    @ExceptionHandler({ResponseStatusException.class})
    public static ResponseEntity<Object> generateException(
            String status,
            String message,
            HttpStatus statusCode
    )   {
        Map<String, Object> responseMap = new HashMap<>();
        Map<String, String> messageMap  = new HashMap<>();
        messageMap.put("message", message);
        responseMap.put("status", status);
        responseMap.put("data", messageMap);

        return new ResponseEntity<>(responseMap, statusCode);
    }
}
