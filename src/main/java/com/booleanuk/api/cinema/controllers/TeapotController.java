package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Teapot;
import com.booleanuk.api.cinema.responses.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teapot")
public class TeapotController {

    /**
     * Brews tea if tea is given
     * @param teapot - Teapot which has been given liquid contents
     * @return Response Entity showing results
     */
    @PostMapping()
    public ResponseEntity<Object> brewTea(@RequestBody Teapot teapot)  {
        if(teapot.getContents().equalsIgnoreCase("Tea"))
            return ResponseHandler.generateResponse("Successfully brewed tea",
                    HttpStatus.OK,
                    teapot);
        else if(teapot.getContents().equalsIgnoreCase("Coffee"))
            return ResponseHandler.generateException(
                    "Error",
                    "Can't brew coffee, I am a teapot",
                    HttpStatus.I_AM_A_TEAPOT
            );
        else return ResponseHandler.generateException(
                "Error",
                    "I don't know what this is, but I don't want it. I want tea",
                    HttpStatus.I_AM_A_TEAPOT
            );
    }
}
