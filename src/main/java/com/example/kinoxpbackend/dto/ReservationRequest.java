package com.example.kinoxpbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationRequest(
        Long id,
        String customerName,
        String customerEmail,
        LocalDateTime creationDate,
        double price,
        Long screeningId,
        List<Long> seatIds) {
}
