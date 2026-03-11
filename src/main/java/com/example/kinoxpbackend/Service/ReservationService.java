package com.example.kinoxpbackend.Service;


import com.example.kinoxpbackend.Exception.NotfoundException;
import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.Model.Seat;
import com.example.kinoxpbackend.Repository.ReservationRepository;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import com.example.kinoxpbackend.Repository.SeatRepository;
import com.example.kinoxpbackend.dto.ReservationRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    public ReservationService(ReservationRepository reservationRepository, ScreeningRepository screeningRepository, SeatRepository seatRepository){
        this.reservationRepository = reservationRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
    }

    //metode der henter alle reservations
    public List<ReservationRequest> getAllReservations(){
        var reservations = reservationRepository.findAll();
        List<ReservationRequest> requests = new ArrayList<>();
        for (Reservation reservation : reservations) {
            requests.add(reservationRequestMapper(reservation));
        }

        return requests;
    }

    private Reservation reservationMapper(ReservationRequest request){
        Reservation reservation = new Reservation();
        reservation.setCustomerName(request.customerName());
        reservation.setCustomerEmail(request.customerEmail());
        reservation.setCreationDate(request.creationDate());
        reservation.setPrice(request.price());
        reservation.setId(request.id());
        return reservation;
    }

    private ReservationRequest reservationRequestMapper(Reservation reservation){
        return new ReservationRequest(
                reservation.getId(),
                reservation.getCustomerName(),
                reservation.getCustomerEmail(),
                reservation.getCreationDate(),
                reservation.getPrice(),
                reservation.getScreening().getId(),
                reservation.getSeats()
                        .stream()
                        .map(Seat::getId)
                        .toList()
        );
    }

    //metode der henter en bestemt reservations på id
    public ReservationRequest getReservationById(Long id){
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if(reservationOptional.isEmpty()){
         throw new NotfoundException("Reservation with id " + id + " not found");
        }

        return reservationRequestMapper(reservationOptional.get());
    }

    public ReservationRequest createReservation(ReservationRequest request) {
        Reservation reservation = reservationMapper(request);

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

        return reservationRequestMapper(saved);
    }

    //Metoder der sletter en reservation
    public void deleteReservation(Long id){
     reservationRepository.deleteById(id);
    }


}
