package com.example.kinoxpbackend.Controller;

import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //Henter alle reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }


    //Henter en bestem reservation på id
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id){
       return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    //Laver en reservation
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        Reservation newReservation = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    //Sletter en reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
