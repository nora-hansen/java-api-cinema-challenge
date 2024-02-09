package com.booleanuk.api.cinema.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(
            String message,
            HttpStatus status,
            Object object) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", message);
        responseMap.put("data", object);

        return new ResponseEntity<>(responseMap, status);
    }
}
