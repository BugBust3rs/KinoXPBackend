package com.example.kinoxpbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ScreeningResponse(
        Long id,
        MovieResponse movie,
        HallResponse hall,
        LocalDateTime startTime,
        double basePrice,
        boolean is3D,
        List<SeatResponse> seatResponseList
) {
}
