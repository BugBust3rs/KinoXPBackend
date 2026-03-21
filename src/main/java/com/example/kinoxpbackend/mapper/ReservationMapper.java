package com.example.kinoxpbackend.mapper;

import com.example.kinoxpbackend.reservation.Reservation;
import com.example.kinoxpbackend.dto.ReservationRequest;
import com.example.kinoxpbackend.dto.ReservationResponse;

public class ReservationMapper {

    public static Reservation requestToReservationMapper(ReservationRequest request){
        Reservation reservation = new Reservation();
        reservation.setCustomerName(request.customerName());
        reservation.setCustomerEmail(request.customerEmail());
        reservation.setCreationDate(request.creationDate());
        reservation.setPrice(request.price());
        return reservation;
    }

    public static ReservationResponse reservationToResponseMapper(Reservation reservation){
        return new ReservationResponse(
                reservation.getId(),
                reservation.getCustomerName(),
                reservation.getCustomerEmail(),
                reservation.getCreationDate(),
                reservation.getPrice(),
                reservation.getScreening().getId(),
                reservation.getSeats()
        );
    }
}
