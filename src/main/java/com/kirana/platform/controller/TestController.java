package com.kirana.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/protected")
    public ResponseEntity<String> getProtectedData(Principal principal) {
        String email = principal != null ? principal.getName() : "Unknown";
        return ResponseEntity.ok("Success! You are authenticated as: " + email);
    }
}