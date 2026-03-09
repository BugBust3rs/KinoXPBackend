package com.example.kinoxpbackend.Controller;

import com.example.kinoxpbackend.Model.Hall;
import com.example.kinoxpbackend.Service.HallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public ResponseEntity<List<Hall>> getAllHalls() {
        return ResponseEntity.ok(hallService.getAllHalls());
    }

    @PostMapping
    public ResponseEntity<Hall> createHall(@RequestBody Hall hall) {
        Hall newHall = hallService.createHall(hall);
        return ResponseEntity.created(URI.create("/halls/" + newHall.getId())).body(newHall);
    }
}
