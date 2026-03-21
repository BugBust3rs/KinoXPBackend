package com.example.kinoxpbackend.reservation;


import com.example.kinoxpbackend.cinema.HallService;
import com.example.kinoxpbackend.cinema.SeatService;
import com.example.kinoxpbackend.dto.ScreeningRequest;
import com.example.kinoxpbackend.dto.ScreeningResponse;
import com.example.kinoxpbackend.exception.NotfoundException;
import com.example.kinoxpbackend.mapper.ScreeningMapper;
import com.example.kinoxpbackend.movie.MovieService;
import com.example.kinoxpbackend.screening.Screening;
import com.example.kinoxpbackend.cinema.Seat;
import com.example.kinoxpbackend.dto.ReservationRequest;
import com.example.kinoxpbackend.dto.ReservationResponse;
import com.example.kinoxpbackend.mapper.ReservationMapper;
import com.example.kinoxpbackend.screening.ScreeningService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningService screeningService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final HallService hallService;

    public ReservationService(ReservationRepository reservationRepository, ScreeningService screeningService,
                              SeatService seatService, MovieService movieService, HallService hallService){
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.seatService = seatService;
        this.movieService = movieService;
        this.hallService = hallService;
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

    @Transactional(readOnly=true)
    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = ReservationMapper.requestToReservationMapper(request);
        Screening screening = screeningService.getScreeningById(request.screeningId());

        for (Long seatId : request.seatIds()) {
            Seat seat = seatService.findById(seatId);
            seat.setReserved(true);
            reservation.addSeat(seat);
        }
        screening.addReservation(reservation);
        var saved = reservationRepository.save(reservation);

        return ReservationMapper.reservationToResponseMapper(saved);
    }

    //Metoder der sletter en reservation
    public void deleteReservation(Long id){
     reservationRepository.deleteById(id);
    }


    public void deleteAll() {
        reservationRepository.deleteAll();
    }
}
