package com.senla.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class HomeStabController {
    @GetMapping
    public ResponseEntity<String> home(){
        return new ResponseEntity<>("HOME STUB", HttpStatus.OK);
    }
}
