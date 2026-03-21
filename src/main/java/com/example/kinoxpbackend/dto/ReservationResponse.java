package com.example.kinoxpbackend.dto;

import com.example.kinoxpbackend.cinema.Seat;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponse(
        Long id,
        String customerName,
        String customerEmail,
        LocalDateTime creationDate,
        double price,
        Long screeningId,
        List<Seat> seats) {}
