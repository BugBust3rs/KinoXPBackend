package com.example.kinoxpbackend.dto;

public record MovieRequest(

    String title,
    int durationMinutes,
    String description,
    String category,
    int ageLimit,
    String image

) {
}
