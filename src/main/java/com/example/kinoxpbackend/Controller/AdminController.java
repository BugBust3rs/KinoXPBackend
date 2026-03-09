package com.example.kinoxpbackend.Controller;

import com.example.kinoxpbackend.Service.AdminService;
import com.example.kinoxpbackend.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest body, HttpSession session) {
        if (adminService.getAdmin(body.username(), body.password())) {
            session.setAttribute("admin", true);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Boolean>> checkSession(HttpSession session) {
        boolean active = session.getAttribute("admin") != null;
        return ResponseEntity.ok(Map.of("active", active));
    }
}