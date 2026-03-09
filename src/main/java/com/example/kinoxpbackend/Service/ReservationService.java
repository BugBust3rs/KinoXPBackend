package com.example.kinoxpbackend.Service;


import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Repository.ReservationRepository;
import exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    //metode der henter alle reservations
    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    //metode der henter en bestemt reservations på id
    public Reservation getReservationById(Long id){
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if(reservationOptional.isEmpty()){
         throw new NotFoundException("Reservation with id " + id + " not found");
        }

        return reservationOptional.get();
    }

    //metode der laver en reservation
    public Reservation createReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    //Metoder der sletter en reservation
    public void deleteReservation(Long id){
     reservationRepository.deleteById(id);
    }


}
