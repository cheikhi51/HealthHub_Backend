package com.healthhub.healthhub.controller;


import com.healthhub.healthhub.dto.LoginDTO;
import com.healthhub.healthhub.dto.RegisterDTO;
import com.healthhub.healthhub.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        String result = utilisateurService.register(registerDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) {
        String result = utilisateurService.login(loginDto);
        return ResponseEntity.ok(result);
    }
}
