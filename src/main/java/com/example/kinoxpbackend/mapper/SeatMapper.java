package com.example.kinoxpbackend.mapper;

import com.example.kinoxpbackend.Model.Seat;
import com.example.kinoxpbackend.dto.ReservationResponse;
import com.example.kinoxpbackend.dto.SeatResponse;

public class SeatMapper {
    public static SeatResponse SeatToSeatResponse(Seat seat) {
        return new SeatResponse(
                seat.getId(),
                seat.getScreening().getId(),
                seat.isReserved(),
                seat.getModularSeating(),
                seat.getSeatRow(),
                seat.getSeatColumn()
        );
    }
}
