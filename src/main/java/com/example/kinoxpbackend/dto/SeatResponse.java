package com.example.kinoxpbackend.dto;

import com.example.kinoxpbackend.cinema.ModularSeating;

public record SeatResponse(
        Long id,
        Long ScreeningId,
        boolean isReserved,
        ModularSeating modularSeating,
        int seatRow,
        int seatColumn

) {
}
