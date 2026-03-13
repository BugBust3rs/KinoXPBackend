package com.example.kinoxpbackend.mapper;

import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.dto.ReservationRequest;
import com.example.kinoxpbackend.dto.ScreeningRequest;

public class ScreeningMapper {
    public static Screening requestToScreeningMapper(ScreeningRequest request){
        Screening screening= new Screening();
        screening.setBasePrice(request.basePrice());
        screening.setIs3D(request.is3D());
        screening.setStartTime(request.startTime());
        return screening;
    }
}
