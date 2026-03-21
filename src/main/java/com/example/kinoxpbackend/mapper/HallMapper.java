package com.example.kinoxpbackend.mapper;

import com.example.kinoxpbackend.cinema.Hall;
import com.example.kinoxpbackend.dto.HallResponse;

public class HallMapper {

    public static HallResponse hallToHallResponse(Hall hall) {
        return new HallResponse(
                hall.getId(),
                hall.getName(),
                hall.getRows(),
                hall.getCols()
        );
    }
}
