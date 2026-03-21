package com.example.kinoxpbackend.dto;

public record MovieResponse(
        Long id,
        String title,
        int durationMinutes,
        String description,
        String category,
        int ageLimit,
        byte[] image
) {
}
