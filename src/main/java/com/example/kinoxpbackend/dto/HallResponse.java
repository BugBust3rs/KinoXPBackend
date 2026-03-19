package com.example.kinoxpbackend.dto;

public record HallResponse(
        Long id,
        String name,
        int rows,
        int cols
) {
}
