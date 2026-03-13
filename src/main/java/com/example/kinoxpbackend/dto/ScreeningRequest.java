package com.example.kinoxpbackend.dto;

import java.time.LocalDateTime;

public record ScreeningRequest(
    Long movieId,
    Long hallId,
    LocalDateTime startTime,
    double basePrice,
    boolean is3D
) {
}
