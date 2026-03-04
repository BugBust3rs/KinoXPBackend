package com.example.kinoxpbackend.Controller;


import com.example.kinoxpbackend.Service.AdminService;
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

    @GetMapping()
    public ResponseEntity<Void> login(@RequestParam String username, @RequestParam String pw, HttpSession session) {
        Admin admin = adminService.getAdmin(username, pw);
        if (admin != null) {
            session.setAttribute("admin", true);
        }
        return ResponseEntity.ok().build();
    }

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


