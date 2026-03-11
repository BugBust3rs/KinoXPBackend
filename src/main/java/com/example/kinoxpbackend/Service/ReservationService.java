package com.example.kinoxpbackend.Service;


import com.example.kinoxpbackend.Exception.NotfoundException;
import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.Model.Seat;
import com.example.kinoxpbackend.Repository.ReservationRepository;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import com.example.kinoxpbackend.Repository.SeatRepository;
import com.example.kinoxpbackend.dto.ReservationRequest;
import com.example.kinoxpbackend.dto.ReservationResponse;
import com.example.kinoxpbackend.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    public ReservationService(ReservationRepository reservationRepository, ScreeningRepository screeningRepository,
                              SeatRepository seatRepository){
        this.reservationRepository = reservationRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
    }

    //metode der henter alle reservations
    public List<ReservationResponse> getAllReservations(){
        var reservations = reservationRepository.findAll();
        List<ReservationResponse> requests = new ArrayList<>();
        for (Reservation reservation : reservations) {
            requests.add(ReservationMapper.reservationToResponseMapper(reservation));
        }

        return requests;
    }



    //metode der henter en bestemt reservations på id
    public ReservationResponse getReservationById(Long id){
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if(reservationOptional.isEmpty()){
         throw new NotfoundException("Reservation with id " + id + " not found");
        }

        return ReservationMapper.reservationToResponseMapper(reservationOptional.get());
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = ReservationMapper.requestToReservationMapper(request);

        Screening screening = screeningRepository.findById(request.screeningId())
                .orElseThrow(() -> new RuntimeException("Screening not found"));
        reservation.setScreening(screening);

        for (Long seatId : request.seatIds()) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            seat.setReserved(true);
            reservation.addSeat(seat);
        }
        var saved = reservationRepository.save(reservation);

        return ReservationMapper.reservationToResponseMapper(saved);
    }

    //Metoder der sletter en reservation
    public void deleteReservation(Long id){
     reservationRepository.deleteById(id);
    }


}
