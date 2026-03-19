package com.example.kinoxpbackend.mapper;

import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.dto.*;

import java.util.List;

public class ScreeningMapper {
    public static Screening requestToScreeningMapper(ScreeningRequest request){
        Screening screening= new Screening();
        screening.setBasePrice(request.basePrice());
        screening.setIs3D(request.is3D());
        screening.setStartTime(request.startTime());
        return screening;
    }
    public static ScreeningResponse screeningToScreeningResponse(Screening screening, MovieResponse movieResponse, HallResponse hallResponse, List<SeatResponse> seatResponseList){
        return new ScreeningResponse(
                screening.getId(),
                movieResponse,
                hallResponse,
                screening.getStartTime(),
                screening.getBasePrice(),
                screening.isIs3D(),
                seatResponseList
        );
    }
}
